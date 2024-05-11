package com.example.mad2assignmenttwo.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChampionModel(var uid: String? = "",
                         var championName: String = "N/A",
                         var championDescription: String = "N/A",
                         var winRate: Double = 0.0,
                         var email: String? = "joe@bloggs.com") : Parcelable

{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "championName" to championName,
            "championDescription" to championDescription,
            "winRate" to winRate,
            "email" to email

        )
    }
}
