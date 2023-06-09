package com.sample.characterviewer

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sample.characterviewer.databinding.CharacterListItemBinding
import com.sample.characterviewer.models.CharacterData

class CharacterListAdapter(
    private val onItemClicked: (CharacterData) -> Unit
) : ListAdapter<CharacterData, CharacterListAdapter.CharacterViewHolder>(DiffCallback) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        context = parent.context
        return CharacterViewHolder(
            CharacterListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val currentItem = getItem(position)

        holder.itemView.setOnClickListener {
            onItemClicked(currentItem)
        }
        holder.bind(currentItem)
    }


    class CharacterViewHolder(private var binding: CharacterListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: CharacterData) {
            binding.characterName.text = character.name
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<CharacterData>() {
            override fun areItemsTheSame(oldItem: CharacterData, newItem: CharacterData): Boolean {
                return (oldItem.name == newItem.name)
            }

            override fun areContentsTheSame(
                oldItem: CharacterData,
                newItem: CharacterData
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}