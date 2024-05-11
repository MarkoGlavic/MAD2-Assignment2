package com.example.mad2assignmenttwo.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

interface ChampionStore {
    fun findAll(championsList:
                MutableLiveData<List<ChampionModel>>)
    fun findAll(userid:String,
                championList:
                MutableLiveData<List<ChampionModel>>)
    fun findById(userid:String, championid: String,
                 champion: MutableLiveData<ChampionModel>)
    fun create(firebaseUser: MutableLiveData<FirebaseUser>, champion: ChampionModel)
    fun delete(userid:String, championid: String)
    fun update(userid:String, championid: String, champion: ChampionModel)
}