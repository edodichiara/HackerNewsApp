package com.example.hackernewsapp.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hackernewsapp.R
import com.example.hackernewsapp.databinding.FragmentMyPreferencesBinding
import com.example.hackernewsapp.model.StoryModel
import com.example.hackernewsapp.ui.adapter.StoryListAdapter
import com.example.hackernewsapp.ui.viewmodels.MyPreferencesFragmentViewModel
import com.example.hackernewsapp.ui.viewmodels.SharedViewModel
import com.example.hackernewsapp.utils.StoryListResult
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Edoardo Di Chiara
 */
@AndroidEntryPoint
class MyPreferencesFragment : Fragment() {
    private var _binding: FragmentMyPreferencesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MyPreferencesFragmentViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyPreferencesBinding.inflate(layoutInflater)
        viewModel.retrieveNewStories()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeRepo()
        setPullToRefresh()
    }

    private fun observeRepo() {
        viewModel.newStoryListResult.observe(viewLifecycleOwner) {
            when (it) {
                is StoryListResult.Success -> {
                    setupUi(it.data)
                    if (binding.swipeToRefresh.isRefreshing) binding.swipeToRefresh.isRefreshing =
                        false
                }
                is StoryListResult.Error -> {
                    Snackbar.make(
                        binding.fragmentMyPref,
                        getString(R.string.connection_error),
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction(getString(R.string.retry)) {
                        viewModel.retrieveNewStories()
                    }.show()
                    if (binding.swipeToRefresh.isRefreshing) binding.swipeToRefresh.isRefreshing =
                        false
                }
            }
        }
    }

    private fun setupUi(data: List<StoryModel>) {
        val storyListAdapter = StoryListAdapter(data, viewModel.getStoriesFromMyFavourite(),
            {
                viewModel.saveStoryOnMyFavourite(it)
            },
            {
                viewModel.deleteStoryFromMyFavourite(it)
            }, {id, isCommentEqualToZero ->
                sharedViewModel.selectedId.value = id
                if(isCommentEqualToZero){
                    Toast.makeText(requireContext(), R.string.no_comments, Toast.LENGTH_SHORT).show()
                } else {
                    findNavController().navigate(R.id.action_global_commentScreenFragment)
                }
            }) {
            if (it.url.length > 4) {
                val urlIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(it.url)
                )
                startActivity(urlIntent)
            } else {
                Toast.makeText(requireContext(), R.string.invalid_link, Toast.LENGTH_LONG).show()
            }
        }
        binding.prefsRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            adapter = storyListAdapter
        }
    }

    private fun setPullToRefresh() {
        binding.swipeToRefresh.setOnRefreshListener {
            viewModel.retrieveNewStories()
        }
    }
}