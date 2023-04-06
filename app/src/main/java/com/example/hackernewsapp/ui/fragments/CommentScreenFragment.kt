package com.example.hackernewsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hackernewsapp.R
import com.example.hackernewsapp.databinding.FragmentCommentScreenBinding
import com.example.hackernewsapp.model.CommentModel
import com.example.hackernewsapp.ui.adapter.CommentListAdapter
import com.example.hackernewsapp.ui.viewmodels.CommentScreenViewModel
import com.example.hackernewsapp.ui.viewmodels.SharedViewModel
import com.example.hackernewsapp.utils.CommentListResult
import com.google.android.material.snackbar.Snackbar
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
        setPullToRefresh(sharedViewModel.selectedId.value)
    }

    private fun observeDataFromNewStoriesFragment() {
        sharedViewModel.selectedId.observe(viewLifecycleOwner) {
            Log.d("prova", "observeDataFromNewStoriesFragment: $it")
            viewModel.retrieveRepo(it)
            observeRepo()
        }
    }

    private fun observeRepo() {
        binding.loadingProgressbar.show()
        viewModel.commentListResult.observe(viewLifecycleOwner) {
            binding.loadingProgressbar.hide()
            when (it) {
                is CommentListResult.Success -> {
                    setupUI(it.comments)
                    if (binding.swipeToRefresh.isRefreshing) binding.swipeToRefresh.isRefreshing =
                        false
                }
                is CommentListResult.Error -> {
                    if (binding.swipeToRefresh.isRefreshing) binding.swipeToRefresh.isRefreshing =
                        false
                    Snackbar.make(
                        binding.commentScreenFragment,
                        getString(R.string.connection_error),
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction(getString(R.string.retry)) {
                        sharedViewModel.selectedId.value?.let { it1 -> viewModel.retrieveRepo(it1) }
                    }.show()
                    Log.d("observe repo", "observeRepo: ${it.e.message}")
                }
            }
        }
    }

    private fun setupUI(list: List<CommentModel>){
        val commentListAdapter = CommentListAdapter(list)
        binding.commentsRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            adapter = commentListAdapter
        }
    }

    private fun setPullToRefresh(id: Int?) {
        binding.swipeToRefresh.setOnRefreshListener {
            if (id != null) {
                viewModel.retrieveRepo(id)
            }
        }
    }
}