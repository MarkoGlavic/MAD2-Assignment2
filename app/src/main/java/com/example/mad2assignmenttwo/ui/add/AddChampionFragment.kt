package com.example.mad2assignmenttwo.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.mad2assignmenttwo.R
import com.example.mad2assignmenttwo.databinding.FragmentAddChampionBinding
import com.example.mad2assignmenttwo.main.ChampionApp
import com.example.mad2assignmenttwo.models.ChampionModel
import com.example.mad2assignmenttwo.ui.auth.LoggedInViewModel
import com.example.mad2assignmenttwo.ui.list.ListViewModel
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 * Use the [AddChampionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddChampionFragment : Fragment() {
    private var _fragBinding: FragmentAddChampionBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var addChampionViewModel: AddChampionViewModel
    private val listViewModel: ListViewModel by activityViewModels()
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun setButtonListener(layout: FragmentAddChampionBinding) {
        layout.addChampionButton.setOnClickListener {
            val winRate = if (layout.championWinRateEditText.text.toString().isNotEmpty())
                layout.championWinRateEditText.text.toString().toDouble() else 0.0
            val name = layout.championNameEditText.text.toString()
            val desc = layout.championDescEditText.text.toString()
addChampionViewModel.addChampion(loggedInViewModel.liveFirebaseUser,ChampionModel(championName = name, championDescription = desc, winRate = winRate,
    email = loggedInViewModel.liveFirebaseUser.value?.email!!   )
)
            Timber.i("Champion Added $name")

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentAddChampionBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.action_add)
        setupMenu()
        addChampionViewModel = ViewModelProvider(this).get(AddChampionViewModel::class.java)
        addChampionViewModel.observableStatus.observe(viewLifecycleOwner, Observer {
                status -> status?.let { render(status) }
        })
        setButtonListener(fragBinding)

        return root;

    }


    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // Handle for example visibility of menu items
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_add, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Validate and handle the selected menu item
                return NavigationUI.onNavDestinationSelected(menuItem,
                    requireView().findNavController())
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun render(status: Boolean) {
        when (status) {
            true -> {
                view?.let {
                    //Uncomment this if you want to immediately return to Report
                    //findNavController().popBackStack()
                }
            }
            false -> Toast.makeText(context,getString(R.string.champion_error),Toast.LENGTH_LONG).show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onResume() {
        super.onResume()
        val listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        listViewModel.observableChampionList.observe(viewLifecycleOwner, Observer {

        })
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            AddChampionFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}