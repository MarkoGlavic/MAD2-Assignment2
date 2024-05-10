package com.example.mad2assignmenttwo.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mad2assignmenttwo.firebase.FirebaseDBManager
import com.example.mad2assignmenttwo.models.ChampionModel
import timber.log.Timber

class ChampionDetailViewModel : ViewModel() {
    private val champion = MutableLiveData<ChampionModel>()

    var observableChampion: LiveData<ChampionModel>
        get() = champion
        set(value) {champion.value = value.value}

    fun getChampion(championid:String, id: String) {
        try {
            FirebaseDBManager.findById(championid, id, champion)
            Timber.i("Detail getChampion() Success : ${
                champion.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Detail getChampion() Error : $e.message")
        }
    }

    fun updateChampion(userid:String, id: String,champion: ChampionModel) {
        try {
            FirebaseDBManager.update(userid, id, champion)
            Timber.i("Detail update() Success : $champion")
        }
        catch (e: Exception) {
            Timber.i("Detail update() Error : $e.message")
        }
    }
}