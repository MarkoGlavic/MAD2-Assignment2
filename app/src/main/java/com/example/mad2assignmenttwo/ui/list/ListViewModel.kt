package com.example.mad2assignmenttwo.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mad2assignmenttwo.models.ChampionManager
import com.example.mad2assignmenttwo.models.ChampionModel

class ListViewModel: ViewModel() {

    private val championList = MutableLiveData<List<ChampionModel>>()

    val observableChampionList: LiveData<List<ChampionModel>>
        get() = championList

    init {
        load()
    }

    fun load() {
        championList.value = ChampionManager.findAll()
    }
}