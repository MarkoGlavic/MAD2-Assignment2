package com.example.mad2assignmenttwo.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChampionModel(var uid: String? = "",
                         var championName: String = "N/A",
                         var championDescription: String = "N/A",
                         var winRate: Double = 0.0,
                         var profilepic: String = "",
                         var email: String? = "joe@bloggs.com",
                         var isFavorite: Boolean = false) : Parcelable

{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "championName" to championName,
            "championDescription" to championDescription,
            "winRate" to winRate,
            "profilepic" to profilepic,
            "email" to email,
            "isFavourite" to isFavorite

        )
    }
}
