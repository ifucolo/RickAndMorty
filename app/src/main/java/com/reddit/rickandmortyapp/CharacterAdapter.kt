package com.reddit.rickandmortyapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.reddit.rickandmortyapp.databinding.CharacterItemViewBinding
import com.reddit.rickandmortyapp.network.RickAndMortyCharacter

class CharacterAdapter(private val list: List<RickAndMortyCharacter>) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = CharacterItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = list[position]
        val context = holder.itemView.context
        with(holder.binding) {
            characterName.text = character.name

            Glide.with(context)
                .load(character.image)
                .into(imageView)
        }
    }

    class CharacterViewHolder(val binding: CharacterItemViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = list.size
}