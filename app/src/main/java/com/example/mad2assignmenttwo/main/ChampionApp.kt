package com.example.mad2assignmenttwo.main

import android.app.Application
import com.example.mad2assignmenttwo.models.ChampionMemStore
import com.example.mad2assignmenttwo.models.ChampionStore
import timber.log.Timber

class ChampionApp : Application() {
    lateinit var championStore: ChampionStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        championStore = ChampionMemStore()
        Timber.i("Starting Champion Application")



    }
}