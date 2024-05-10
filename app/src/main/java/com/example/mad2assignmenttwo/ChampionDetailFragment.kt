package com.example.mad2assignmenttwo

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs

class ChampionDetailFragment : Fragment() {

    private val args by navArgs<ChampionDetailFragmentArgs>()

    companion object {
        fun newInstance() = ChampionDetailFragment()
    }

    private lateinit var viewModel: ChampionDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_champion_detail, container, false)
        Toast.makeText(context,"Champion ID Selected : ${args.championid}",Toast.LENGTH_LONG).show()
return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChampionDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}