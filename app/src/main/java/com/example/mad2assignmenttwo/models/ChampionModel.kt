package com.example.mad2assignmenttwo.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChampionModel(var id: Long = 0,
                         val championName: String = "N/A",
                         val championDescription: String = "N/A",
                         val winRate: Double = 0.0) : Parcelable