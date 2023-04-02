package com.example.hackernewsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.hackernewsapp.R
import com.example.hackernewsapp.databinding.FragmentHomeScreenBinding
import com.example.hackernewsapp.databinding.FragmentNewStoriesBinding
import com.example.hackernewsapp.ui.viewmodels.NewStoriesFragmentViewModel
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
        return binding.root
    }

}