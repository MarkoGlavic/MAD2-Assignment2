package com.example.mad2assignmenttwo.models

import timber.log.Timber

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

object ChampionManager : ChampionStore {

    val champions = ArrayList<ChampionModel>()

    override fun findAll(): List<ChampionModel> {
        return champions
    }

    override fun findById(id:Long) : ChampionModel? {
        val foundChampion: ChampionModel? = champions.find { it.id == id }
        return foundChampion
    }

    override fun create(champion: ChampionModel) {
        champion.id = getId()
        champions.add(champion)
        logAll()
    }

    fun logAll() {
        Timber.v("** Champions List **")
        champions.forEach { Timber.v("Champion ${it}") }
    }
}