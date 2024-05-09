package com.example.mad2assignmenttwo.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mad2assignmenttwo.databinding.ActivityMainBinding
import com.example.mad2assignmenttwo.main.ChampionApp
import com.example.mad2assignmenttwo.models.ChampionModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var championLayout : ActivityMainBinding
    lateinit var app: ChampionApp


    override fun onCreate(savedInstanceState: Bundle?) {
        championLayout = ActivityMainBinding.inflate(layoutInflater)
        app = this.application as ChampionApp

        super.onCreate(savedInstanceState)
        setContentView(championLayout.root)



        championLayout.addChampionButton.setOnClickListener {
            val winRate = if (championLayout.championWinRateEditText.text.isNotEmpty())
                championLayout.championWinRateEditText.text.toString().toDouble() else 0.0

               val name = championLayout.championNameEditText.text.toString()
            val desc = championLayout.championDescEditText.text.toString()
                app.championStore.create(ChampionModel(championName = name, championDescription = desc, winRate = winRate))
                Timber.i("Champion Added $name")

        }
    }


}