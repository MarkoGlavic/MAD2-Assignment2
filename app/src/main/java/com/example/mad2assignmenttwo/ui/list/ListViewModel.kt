package com.example.mad2assignmenttwo.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mad2assignmenttwo.firebase.FirebaseDBManager
import com.example.mad2assignmenttwo.models.ChampionModel
import com.google.firebase.auth.FirebaseUser
import timber.log.Timber
import java.lang.Exception

class ListViewModel: ViewModel() {



    private val championList = MutableLiveData<List<ChampionModel>>()

    val observableChampionList: LiveData<List<ChampionModel>>
        get() = championList

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()



    init {
        load()
    }

    fun load() {
        try {
            //DonationManager.findAll(liveFirebaseUser.value?.email!!, donationsList)
            FirebaseDBManager.findAll(liveFirebaseUser.value?.uid!!,championList)
            Timber.i("Report Load Success : ${championList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Report Load Error : $e.message")
        }
    }

    fun delete(userid: String, id: String) {
        try {
            //DonationManager.delete(userid,id)
            FirebaseDBManager.delete(userid,id)
            Timber.i("Report Delete Success")
        }
        catch (e: Exception) {
            Timber.i("Report Delete Error : $e.message")
        }
    }
}