package com.example.mad2assignmenttwo.ui.list

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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mad2assignmenttwo.R
import com.example.mad2assignmenttwo.adapters.ChampionAdapter
import com.example.mad2assignmenttwo.databinding.FragmentListBinding
import com.example.mad2assignmenttwo.main.ChampionApp
import com.example.mad2assignmenttwo.models.ChampionModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */class ListFragment : Fragment() {
    lateinit var app: ChampionApp

    private var _fragBinding: FragmentListBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var listViewModel: ListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

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
                // Validate and handle the selected menu item
                return NavigationUI.onNavDestinationSelected(menuItem,
                    requireView().findNavController())
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentListBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        setupMenu()
        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        listViewModel.observableChampionList.observe(viewLifecycleOwner, Observer {
                champions ->
            champions?.let { render(champions) }
        })

        val fab: FloatingActionButton = fragBinding.fab
        fab.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToAddChampionFragment()
            findNavController().navigate(action)
        }

        return root
    }

    private fun render(championsList: List<ChampionModel>) {
        fragBinding.recyclerView.adapter = ChampionAdapter(championsList)
        if (championsList.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.championsNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.championsNotFound.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        listViewModel.load()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}