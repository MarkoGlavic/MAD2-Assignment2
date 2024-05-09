package com.example.mad2assignmenttwo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mad2assignmenttwo.R
import com.example.mad2assignmenttwo.adapters.ChampionAdapter
import com.example.mad2assignmenttwo.databinding.ActivityListChampionsBinding
import com.example.mad2assignmenttwo.main.ChampionApp

class ListChampions : AppCompatActivity() {

    lateinit var app: ChampionApp
    lateinit var listLayout : ActivityListChampionsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listLayout = ActivityListChampionsBinding.inflate(layoutInflater)
        setContentView(listLayout.root)


        app = this.application as ChampionApp
        listLayout.recyclerView.layoutManager = LinearLayoutManager(this)
        listLayout.recyclerView.adapter = ChampionAdapter(app.championStore.findAll())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_add -> {
                startActivity(Intent(this, AddChampion::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}