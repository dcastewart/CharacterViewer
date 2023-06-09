package com.sample.characterviewer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.sample.characterviewer.databinding.FragmentCharacterDetailsBinding

class CharacterDetailsFragment : Fragment() {

    private val characterViewModel: CharacterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentCharacterDetailsBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCharacterDetailsBinding.bind(view)

        characterViewModel.currentCharacter.observe(this.viewLifecycleOwner) {

            binding.characterDescription.text = it.description
            Glide.with(this)
                .load(it.image)
                .placeholder(R.drawable.placeholder_character)
                .into(binding.characterImage)
        }
    }
}