package com.example.mad2assignmenttwo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.mad2assignmenttwo.R
import com.example.mad2assignmenttwo.databinding.CardChampionBinding
import com.example.mad2assignmenttwo.models.ChampionModel
import com.example.mad2assignmenttwo.utils.customTransformation
import com.squareup.picasso.Picasso


interface ChampionClickListener {
    fun onChampionClick(champion: ChampionModel)
}
class ChampionAdapter constructor(private var champions: ArrayList<ChampionModel>,
                                  private val listener: ChampionClickListener,
                                  private val readOnly: Boolean)
    : RecyclerView.Adapter<ChampionAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardChampionBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding,readOnly)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val champions = champions[holder.adapterPosition]
        holder.bind(champions, listener)
    }

    override fun getItemCount(): Int = champions.size

    fun removeAt(position: Int) {
        champions.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class MainHolder(val binding : CardChampionBinding,private val readOnly : Boolean) : RecyclerView.ViewHolder(binding.root) {
        val readOnlyRow = readOnly

        fun bind(champion:ChampionModel,listener: ChampionClickListener) {
//            binding.championName.text = champion.championName
//            binding.championDescription.text = champion.championDescription
//            binding.championWinRate.text = champion.winRate.toString
            binding.root.tag = champion
            binding.champion=champion
            binding.championImage.setImageResource(R.mipmap.ic_yasuo)
            Picasso.get().load(champion.profilepic.toUri())
                .resize(200, 200)
                .transform(customTransformation())
                .centerCrop()
                .into(binding.championImage)
            binding.root.setOnClickListener { listener.onChampionClick(champion) }
            binding.executePendingBindings()

        }
    }
}