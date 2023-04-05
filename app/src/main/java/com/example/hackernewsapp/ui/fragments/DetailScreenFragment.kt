package com.example.hackernewsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hackernewsapp.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Edoardo Di Chiara
 */
@AndroidEntryPoint
class DetailScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_screen, container, false)
    }
}