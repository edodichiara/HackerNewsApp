package com.example.hackernewsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.hackernewsapp.R
import com.example.hackernewsapp.databinding.FragmentMyPreferencesBinding
import com.example.hackernewsapp.databinding.FragmentNewStoriesBinding
import com.example.hackernewsapp.ui.viewmodels.MyPreferencesFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPreferencesFragment : Fragment() {
    private var _binding: FragmentMyPreferencesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MyPreferencesFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyPreferencesBinding.inflate(layoutInflater)
        return binding.root
    }
}