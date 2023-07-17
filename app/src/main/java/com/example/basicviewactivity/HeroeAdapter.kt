package com.example.basicviewactivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.basicviewactivity.databinding.ItemHeroeBinding
import com.squareup.picasso.Picasso

class HeroeAdapter(val heroes: List<Triple<CharacterResponse, CharacterPowerStats, CharacterImage>>) : RecyclerView.Adapter<HeroeAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = heroes[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_heroe, parent, false))
    }

    override fun getItemCount(): Int {
        return heroes.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemHeroeBinding.bind(view)

        fun bind(hero: Triple<CharacterResponse, CharacterPowerStats, CharacterImage>) {
            val character = hero.first
            val powerStats = hero.second
            val characterImage = hero.third

            binding.tvSuperHero.text = character.name
            binding.tvIntelligence.text = "Intelligence: ${powerStats.intelligence}"
            binding.tvStrength.text = "Strength: ${powerStats.strength}"
            binding.tvSpeed.text = "Speed: ${powerStats.speed}"
            binding.tvDurability.text = "Durability: ${powerStats.durability}"
            binding.tvPower.text = "Power: ${powerStats.power}"
            binding.tvCombat.text = "Combat: ${powerStats.combat}"

            Picasso.get().load(characterImage.url).into(binding.ivHeroImage)
        }

    }

}
