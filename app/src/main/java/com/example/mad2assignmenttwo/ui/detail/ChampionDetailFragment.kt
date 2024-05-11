package com.example.mad2assignmenttwo.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mad2assignmenttwo.R
import com.example.mad2assignmenttwo.databinding.FragmentChampionDetailBinding
import com.example.mad2assignmenttwo.ui.auth.LoggedInViewModel
import com.example.mad2assignmenttwo.ui.list.ListViewModel
import timber.log.Timber

class ChampionDetailFragment : Fragment() {

    private val args by navArgs<ChampionDetailFragmentArgs>()
    private lateinit var championViewModel: ChampionDetailViewModel

    private var _fragBinding: FragmentChampionDetailBinding? = null
    private val fragBinding get() = _fragBinding!!

    private val loggedInViewModel : LoggedInViewModel by activityViewModels()
    private val listViewModel : ListViewModel by activityViewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentChampionDetailBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.championDetails)

        championViewModel = ViewModelProvider(this).get(ChampionDetailViewModel::class.java)
        championViewModel.observableChampion.observe(viewLifecycleOwner, Observer { render() })

        fragBinding.editChampionButton.setOnClickListener {
            championViewModel.updateChampion(loggedInViewModel.liveFirebaseUser.value?.uid!!,
                args.championid, fragBinding.championvm?.observableChampion!!.value!!)
            findNavController().navigateUp()
        }

        fragBinding.deleteChampionButton.setOnClickListener {
            listViewModel.delete(loggedInViewModel.liveFirebaseUser.value?.email!!,
                championViewModel.observableChampion.value?.uid!!)
            findNavController().navigateUp()
        }

        return root
    }

    private fun render() {
        fragBinding.editName.setText("A Name")
        fragBinding.editWinrate.setText("0")
        fragBinding.editDesc.setText("A Desc")
        fragBinding.championvm = championViewModel
    }

    override fun onResume() {
        super.onResume()
        championViewModel.getChampion(loggedInViewModel.liveFirebaseUser.value?.uid!!,
            args.championid)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}