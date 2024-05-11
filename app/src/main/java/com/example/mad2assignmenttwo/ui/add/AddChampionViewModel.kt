package com.example.mad2assignmenttwo.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mad2assignmenttwo.firebase.FirebaseDBManager
import com.example.mad2assignmenttwo.firebase.FirebaseImageManager
import com.example.mad2assignmenttwo.models.ChampionModel
import com.google.firebase.auth.FirebaseUser

class AddChampionViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addChampion(firebaseUser: MutableLiveData<FirebaseUser>,champion: ChampionModel) {
        status.value = try {
            champion.profilepic = FirebaseImageManager.imageUri.value.toString()
            FirebaseDBManager.create(firebaseUser,champion)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}