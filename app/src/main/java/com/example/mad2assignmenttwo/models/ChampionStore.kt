package com.example.mad2assignmenttwo.models

interface ChampionStore {
    fun findAll() : List<ChampionModel>
    fun findById(id: Long) : ChampionModel?
    fun create(champion: ChampionModel)
}