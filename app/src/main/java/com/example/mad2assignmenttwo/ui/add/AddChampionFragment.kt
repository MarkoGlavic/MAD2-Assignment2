package com.example.mad2assignmenttwo.ui.add

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
import androidx.navigation.ui.NavigationUI
import com.example.mad2assignmenttwo.R
import com.example.mad2assignmenttwo.databinding.FragmentAddChampionBinding
import com.example.mad2assignmenttwo.main.ChampionApp
import com.example.mad2assignmenttwo.models.ChampionModel
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 * Use the [AddChampionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddChampionFragment : Fragment() {
    lateinit var app: ChampionApp
    private var _fragBinding: FragmentAddChampionBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var addChampionViewModel: AddChampionViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as ChampionApp
//        setHasOptionsMenu(true)



    }

    fun setButtonListener(layout: FragmentAddChampionBinding) {
        layout.addChampionButton.setOnClickListener {
            val winRate = if (layout.championWinRateEditText.text.isNotEmpty())
                layout.championWinRateEditText.text.toString().toDouble() else 0.0
            val name = layout.championNameEditText.text.toString()
            val desc = layout.championDescEditText.text.toString()
            app.championStore.create(
                ChampionModel(
                    championName = name,
                    championDescription = desc,
                    winRate = winRate
                )
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
        addChampionViewModel =
            ViewModelProvider(this).get(AddChampionViewModel::class.java)
        //val textView: TextView = root.findViewById(R.id.text_home)
        addChampionViewModel.text.observe(viewLifecycleOwner, Observer {
            //textView.text = it
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



    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onResume() {
        super.onResume()

    }
    companion object {
        @JvmStatic
        fun newInstance() =
            AddChampionFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}