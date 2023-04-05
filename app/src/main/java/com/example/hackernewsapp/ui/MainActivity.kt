package com.example.hackernewsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hackernewsapp.R
import com.example.hackernewsapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Edoardo Di Chiara
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = this.findNavController(R.id.fragmentContainerView)
        binding.bottomNavigationMenu.setupWithNavController(navController)
    }
}