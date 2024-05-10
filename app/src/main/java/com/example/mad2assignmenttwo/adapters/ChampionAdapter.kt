package com.example.mad2assignmenttwo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mad2assignmenttwo.R
import com.example.mad2assignmenttwo.databinding.CardChampionBinding
import com.example.mad2assignmenttwo.models.ChampionModel



interface ChampionClickListener {
    fun onChampionClick(champion: ChampionModel)
}
class ChampionAdapter constructor(private var champions: List<ChampionModel>,
                                  private val listener: ChampionClickListener)
    : RecyclerView.Adapter<ChampionAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardChampionBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val champions = champions[holder.adapterPosition]
        holder.bind(champions, listener)
    }

    override fun getItemCount(): Int = champions.size

    inner class MainHolder(val binding : CardChampionBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(champion:ChampionModel,listener: ChampionClickListener) {
//            binding.championName.text = champion.championName
//            binding.championDescription.text = champion.championDescription
//            binding.championWinRate.text = champion.winRate.toString

            binding.champion=champion
            binding.championImage.setImageResource(R.mipmap.ic_yasuo)
            binding.root.setOnClickListener { listener.onChampionClick(champion) }
            binding.executePendingBindings()

        }
    }
}