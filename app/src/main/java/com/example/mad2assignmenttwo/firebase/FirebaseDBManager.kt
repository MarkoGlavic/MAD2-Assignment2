package com.example.mad2assignmenttwo.firebase

import androidx.lifecycle.MutableLiveData
import com.example.mad2assignmenttwo.models.ChampionModel
import com.example.mad2assignmenttwo.models.ChampionStore
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import timber.log.Timber


object FirebaseDBManager : ChampionStore {

    var database: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun findAll(championList: MutableLiveData<List<ChampionModel>>) {
        database.child("champions")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase Champion error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<ChampionModel>()
                    val children = snapshot.children
                    children.forEach {
                        val champion = it.getValue(ChampionModel::class.java)
                        localList.add(champion!!)
                    }
                    database.child("champions")
                        .removeEventListener(this)

                    championList.value = localList
                }
            })
    }

    fun updateImageRef(userid: String,imageUri: String) {

        val userChampions = database.child("user-champions").child(userid)
        val allChampions = database.child("champions")

        userChampions.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        //Update Users imageUri
                        it.ref.child("profilepic").setValue(imageUri)
                        //Update all donations that match 'it'
                        val champion = it.getValue(ChampionModel::class.java)
                        allChampions.child(champion!!.uid!!)
                            .child("profilepic").setValue(imageUri)
                    }
                }
            })
    }
    override fun findAll(userid: String, championList: MutableLiveData<List<ChampionModel>>) {

        database.child("user-champions").child(userid)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase Champion error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<ChampionModel>()
                    val children = snapshot.children
                    children.forEach {
                        val champion = it.getValue(ChampionModel::class.java)
                        localList.add(champion!!)
                    }
                    database.child("user-champions").child(userid)
                        .removeEventListener(this)

                    championList.value = localList
                }
            })
    }

    override fun findById(userid: String, championid: String, champion: MutableLiveData<ChampionModel>) {

        database.child("user-champions").child(userid)
            .child(championid).get().addOnSuccessListener {
                champion.value = it.getValue(ChampionModel::class.java)
                Timber.i("firebase Got value ${it.value}")
            }.addOnFailureListener{
                Timber.e("firebase Error getting data $it")
            }
    }

    override fun create(firebaseUser: MutableLiveData<FirebaseUser>, champion: ChampionModel) {
        Timber.i("Firebase DB Reference : $database")

        val uid = firebaseUser.value!!.uid
        val key = database.child("champions").push().key
        if (key == null) {
            Timber.i("Firebase Error : Key Empty")
            return
        }
        champion.uid = key
        val championValues = champion.toMap()

        val childAdd = HashMap<String, Any>()
        childAdd["/champions/$key"] = championValues
        childAdd["/user-champions/$uid/$key"] = championValues

        database.updateChildren(childAdd)
    }

    override fun delete(userid: String, championid: String) {

        val childDelete : MutableMap<String, Any?> = HashMap()
        childDelete["/champions/$championid"] = null
        childDelete["/user-champions/$userid/$championid"] = null

        database.updateChildren(childDelete)
    }

    override fun update(userid: String, championid: String, champion: ChampionModel) {

        val championValues = champion.toMap()

        val childUpdate : MutableMap<String, Any?> = HashMap()
        childUpdate["champions/$championid"] = championValues
        childUpdate["user-champions/$userid/$championid"] = championValues

        database.updateChildren(childUpdate)
    }

    fun updateFavoriteStatus(champion: ChampionModel, userId: String) {
        val favoriteRef = database.child("user-favourites").child(userId).child(champion.uid!!)
        favoriteRef.setValue(champion.isFavorite)
    }


}