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
import com.example.hackernewsapp.databinding.FragmentNewStoriesBinding
import com.example.hackernewsapp.model.StoryModel
import com.example.hackernewsapp.ui.adapter.StoryListAdapter
import com.example.hackernewsapp.ui.viewmodels.NewStoriesFragmentViewModel
import com.example.hackernewsapp.ui.viewmodels.SharedViewModel
import com.example.hackernewsapp.utils.StoryListResult
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Edoardo Di Chiara
 */
@AndroidEntryPoint
class NewStoriesFragment : Fragment() {
    private var _binding: FragmentNewStoriesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewStoriesFragmentViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewStoriesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pos = arguments?.getInt(POSITION_ARGUMENT)
        observeRepo(pos)
        setPullToRefresh(pos)
    }

    private fun observeRepo(pos: Int?) {
        binding.loadingProgressbar.show()
        pos?.let { position ->
            when (position) {
                0 -> {
                    viewModel.retrieveNewStories()
                    viewModel.newStoryListResult.observe(viewLifecycleOwner) {
                        binding.loadingProgressbar.hide()
                        when (it) {
                            is StoryListResult.Success -> {
                                setupUi(it.data)
                                if (binding.swipeToRefresh.isRefreshing) binding.swipeToRefresh.isRefreshing =
                                    false
                            }
                            is StoryListResult.Error -> {
                                errorHandling()
                            }
                        }
                    }
                }
                1 -> {
                    viewModel.retrieveTopStories()
                    viewModel.topStoryListResult.observe(viewLifecycleOwner) {
                        binding.loadingProgressbar.hide()
                        when (it) {
                            is StoryListResult.Success -> {
                                setupUi(it.data)
                                if (binding.swipeToRefresh.isRefreshing) binding.swipeToRefresh.isRefreshing =
                                    false
                            }
                            is StoryListResult.Error -> {
                                errorHandling()
                            }
                        }
                    }
                }
                2 -> {
                    viewModel.retrieveBestStories()
                    viewModel.bestStoryListResult.observe(viewLifecycleOwner) {
                        binding.loadingProgressbar.hide()
                        when (it) {
                            is StoryListResult.Success -> {
                                setupUi(it.data)
                                if (binding.swipeToRefresh.isRefreshing) binding.swipeToRefresh.isRefreshing =
                                    false
                            }
                            is StoryListResult.Error -> {
                                errorHandling()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun errorHandling() {
        findSuitableViewForSnackbar(requireParentFragment())?.let { it1 ->
            Snackbar.make(
                it1,
                getString(R.string.connection_error),
                Snackbar.LENGTH_INDEFINITE
            ).setAction(getString(R.string.retry)) {
                viewModel.retrieveNewStories()
            }.show()
        }

        if (binding.swipeToRefresh.isRefreshing) binding.swipeToRefresh.isRefreshing =
            false
    }

    companion object {
        var POSITION_ARGUMENT = "position_arg"

        @JvmStatic
        fun newInstance(position: Int) = NewStoriesFragment().apply {
            arguments = Bundle().apply {
                putInt(POSITION_ARGUMENT, position)
            }
        }
    }

    private fun setupUi(data: List<StoryModel>) {
        val storyListAdapter = StoryListAdapter(data, viewModel.getStoriesFromMyFavourite(), {
            viewModel.saveStoryOnMyFavourite(it)
        }, {
            viewModel.deleteStoryFromMyFavourite(it)
        },
            {id, isCommentEqualToZero ->
                sharedViewModel.selectedId.value = id
                if(isCommentEqualToZero){
                    Toast.makeText(requireContext(), R.string.no_comments, Toast.LENGTH_SHORT).show()
                } else {
                    findNavController().navigate(R.id.action_global_commentScreenFragment)
                }

            }) {
            if (it.url.length > 4) {
                callIntentToShowWebsite(it)
            } else {
                Toast.makeText(requireContext(), R.string.invalid_link, Toast.LENGTH_LONG).show()
            }
        }
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

    private fun callIntentToShowWebsite(it: StoryModel) {
        val urlIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(it.url)
        )
        startActivity(urlIntent)
    }

    private fun setPullToRefresh(position: Int?) {
        binding.swipeToRefresh.setOnRefreshListener {
            when(position){
                0 -> viewModel.retrieveNewStories()
                1 -> viewModel.retrieveTopStories()
                2 -> viewModel.retrieveBestStories()
            }
        }
    }

    private fun findSuitableViewForSnackbar(fragment: Fragment): View? {
        val contentView = fragment.activity?.findViewById(android.R.id.content) as? ViewGroup
        return contentView?.getChildAt(0)
    }

}