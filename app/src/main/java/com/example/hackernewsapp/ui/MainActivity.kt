package com.example.hackernewsapp.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hackernewsapp.R
import com.example.hackernewsapp.databinding.ActivityMainBinding
import com.example.hackernewsapp.network.NetworkObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = this.findNavController(R.id.fragmentContainerView)
        binding.bottomNavigationMenu.setupWithNavController(navController)

        lifecycleScope.launch {
            try {
                Log.d("chiamata di rete", "onCreate: ${NetworkObject.service.listTopStories().body()}")
            } catch (e: Exception){
                Log.d("chiamata di rete", "onCreate: ${e}")

                e.printStackTrace()
            }
        }
    }
}