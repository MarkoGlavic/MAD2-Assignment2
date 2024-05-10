package com.example.mad2assignmenttwo.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mad2assignmenttwo.models.ChampionManager
import com.example.mad2assignmenttwo.models.ChampionModel

class AddChampionViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addChampion(champion: ChampionModel) {
        status.value = try {
            ChampionManager.create(champion)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}