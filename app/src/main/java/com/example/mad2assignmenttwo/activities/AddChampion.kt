package com.example.mad2assignmenttwo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.mad2assignmenttwo.R
import com.example.mad2assignmenttwo.databinding.ActivityMainBinding
import com.example.mad2assignmenttwo.main.ChampionApp
import com.example.mad2assignmenttwo.models.ChampionModel
import timber.log.Timber

class AddChampion : AppCompatActivity() {

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
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_list -> {
                startActivity(Intent(this, ListChampions::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



}