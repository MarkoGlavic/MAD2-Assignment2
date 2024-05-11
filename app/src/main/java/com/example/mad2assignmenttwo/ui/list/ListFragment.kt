package com.example.mad2assignmenttwo.ui.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mad2assignmenttwo.R
import com.example.mad2assignmenttwo.adapters.ChampionAdapter
import com.example.mad2assignmenttwo.adapters.ChampionClickListener
import com.example.mad2assignmenttwo.databinding.FragmentListBinding
import com.example.mad2assignmenttwo.main.ChampionApp
import com.example.mad2assignmenttwo.models.ChampionModel
import com.example.mad2assignmenttwo.ui.auth.LoggedInViewModel
import com.example.mad2assignmenttwo.utils.SwipeToDeleteCallback
import com.example.mad2assignmenttwo.utils.SwipeToEditCallback
import com.example.mad2assignmenttwo.utils.createLoader
import com.example.mad2assignmenttwo.utils.hideLoader
import com.example.mad2assignmenttwo.utils.showLoader
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */class ListFragment : Fragment(), ChampionClickListener {
   lateinit var loader : AlertDialog
    private var _fragBinding: FragmentListBinding? = null
    private val fragBinding get() = _fragBinding!!
    private val listViewModel: ListViewModel by activityViewModels()
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // Handle for example visibility of menu items
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_list, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_filter_winrate -> {
                        filterByWinrate()
                        true
                    }
                        R.id.action_filter_alphabetical -> {
                        filterAlphabetically()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


    private fun filterAlphabetically() {
        val sortedList = listViewModel.observableChampionList.value?.sortedBy { it.championName }
        sortedList?.let { render(ArrayList(it)) }
    }
    private fun filterByWinrate() {
        val sortedList = listViewModel.observableChampionList.value?.sortedByDescending { it.winRate }
        sortedList?.let { render(ArrayList(it)) }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentListBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.action_list)
        setupMenu()
        loader = createLoader(requireActivity())
        fragBinding.recyclerView.layoutManager = GridLayoutManager(activity,2)
        fragBinding.fab.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToAddChampionFragment()
            findNavController().navigate(action)
        }
        showLoader(loader,"Downloading Champions")
        listViewModel.observableChampionList.observe(viewLifecycleOwner, Observer {
                champions ->
            champions?.let {
                render(champions as ArrayList<ChampionModel>)
                hideLoader(loader)
                checkSwipeRefresh()
            }
        })

        setSwipeRefresh()
        val swipeDeleteHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showLoader(loader,"Deleting Champions")
                val adapter = fragBinding.recyclerView.adapter as ChampionAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                listViewModel.delete(listViewModel.liveFirebaseUser.value?.uid!!,
                    (viewHolder.itemView.tag as ChampionModel).uid!!)

                hideLoader(loader)
            }
        }

        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(fragBinding.recyclerView)

        val swipeEditHandler = object : SwipeToEditCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onChampionClick(viewHolder.itemView.tag as ChampionModel)
            }
        }
        val itemTouchEditHelper = ItemTouchHelper(swipeEditHandler)
        itemTouchEditHelper.attachToRecyclerView(fragBinding.recyclerView)




        return root
    }

    private fun render(championsList: ArrayList<ChampionModel>) {
        fragBinding.recyclerView.adapter = ChampionAdapter(championsList, this)
        if (championsList.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.championsNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.championsNotFound.visibility = View.GONE
        }
    }
    override fun onChampionClick(champion: ChampionModel) {
        val action = ListFragmentDirections.actionListFragmentToChampionDetailFragment(champion.uid!!)
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        showLoader(loader,"Downloading Champions")
        loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                listViewModel.liveFirebaseUser.value = firebaseUser
                listViewModel.load()
            }
        })
        //hideLoader(loader)
    }

    private fun setSwipeRefresh() {
        fragBinding.swiperefresh.setOnRefreshListener {
            fragBinding.swiperefresh.isRefreshing = true
            showLoader(loader,"Downloading Champions")
            listViewModel.load()

        }
    }

    private fun checkSwipeRefresh() {
        if (fragBinding.swiperefresh.isRefreshing)
            fragBinding.swiperefresh.isRefreshing = false
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}