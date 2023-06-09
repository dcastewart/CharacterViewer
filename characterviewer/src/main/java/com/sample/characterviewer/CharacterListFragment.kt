package com.sample.characterviewer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.sample.characterviewer.databinding.FragmentCharacterListBinding

class CharacterListFragment : Fragment() {

    private val characterViewModel: CharacterViewModel by activityViewModels()
    private lateinit var binding: FragmentCharacterListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentCharacterListBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCharacterListBinding.bind(view)
        val slidingPaneLayout = binding.slidingPaneLayout
        slidingPaneLayout.lockMode = SlidingPaneLayout.LOCK_MODE_LOCKED

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            CharacterListOnBackPressedCallback(slidingPaneLayout)
        )

        populateRecyclerView()

        characterViewModel.characterList.observe(viewLifecycleOwner) {
            populateRecyclerView()
        }

        binding.searchBar.doOnTextChanged { _, _, _, _ ->
            populateRecyclerView()
        }

    }

    private fun populateRecyclerView() {
        val adapter = CharacterListAdapter {
            characterViewModel.updateCurrentCharacter(it)
            binding.slidingPaneLayout.openPane()
        }
        binding.characterListRecyclerView.adapter = adapter
        adapter.submitList(
            characterViewModel.characterList.value
                ?.filter { characterData ->
                    characterData.name.contains(binding.searchBar.text, ignoreCase = true)
                            || characterData.description.contains(binding.searchBar.text, ignoreCase = true)
                }
                ?: emptyList()
        )
    }
}


class CharacterListOnBackPressedCallback(
    private val slidingPaneLayout: SlidingPaneLayout
) : OnBackPressedCallback(
    true
), SlidingPaneLayout.PanelSlideListener {

    init {
        slidingPaneLayout.addPanelSlideListener(this)
    }

    override fun handleOnBackPressed() {
        if (slidingPaneLayout.isOpen) slidingPaneLayout.closePane()
    }

    override fun onPanelSlide(panel: View, slideOffset: Float) {}

    override fun onPanelOpened(panel: View) {}

    override fun onPanelClosed(panel: View) {}
}