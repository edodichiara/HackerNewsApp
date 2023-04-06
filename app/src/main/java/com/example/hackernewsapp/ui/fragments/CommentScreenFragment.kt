package com.example.hackernewsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.hackernewsapp.databinding.FragmentCommentScreenBinding
import com.example.hackernewsapp.ui.viewmodels.CommentScreenViewModel
import com.example.hackernewsapp.ui.viewmodels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Edoardo Di Chiara
 */
@AndroidEntryPoint
class CommentScreenFragment : Fragment() {
    private var _binding: FragmentCommentScreenBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CommentScreenViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommentScreenBinding.inflate(layoutInflater)
        observeDataFromNewStoriesFragment()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun observeDataFromNewStoriesFragment(){
        sharedViewModel.selectedId.observe(viewLifecycleOwner){
            Log.d("prova", "observeDataFromNewStoriesFragment: $it")
            viewModel.retrieveRepo(it)
        }
    }
}