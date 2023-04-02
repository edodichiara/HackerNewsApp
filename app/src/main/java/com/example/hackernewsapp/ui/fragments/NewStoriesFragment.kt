package com.example.hackernewsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hackernewsapp.R
import com.example.hackernewsapp.databinding.FragmentHomeScreenBinding
import com.example.hackernewsapp.databinding.FragmentNewStoriesBinding
import com.example.hackernewsapp.model.StoryModel
import com.example.hackernewsapp.ui.adapter.StoryListAdapter
import com.example.hackernewsapp.ui.viewmodels.NewStoriesFragmentViewModel
import com.example.hackernewsapp.utils.StoryListResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewStoriesFragment : Fragment() {
    private var _binding: FragmentNewStoriesBinding? = null
    private val binding get() = _binding!!
    private val viewModel : NewStoriesFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewStoriesBinding.inflate(layoutInflater)
        viewModel.retrieveStories()
        observeRepo()
        return binding.root
    }

    fun observeRepo(){
        viewModel.storyListResult.observe(viewLifecycleOwner){
            when(it){
                is StoryListResult.Success -> setupUi(it.data)
                is StoryListResult.Error -> Toast.makeText(requireContext(), "Errore di rete", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupUi(data: List<StoryModel>) {
        val storyListAdapter = StoryListAdapter(data)
        binding.recyclerView.apply {
            layoutManager =
                LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            adapter = storyListAdapter
        }
    }

}