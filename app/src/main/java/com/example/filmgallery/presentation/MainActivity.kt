package com.example.filmgallery.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.filmgallery.R
import com.example.filmgallery.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setupNavController()
    }

    private fun setupNavController() {
        val controller = (supportFragmentManager
            .findFragmentById(R.id.container) as NavHostFragment)
            .navController
        binding?.run {
            bottomNav.setupWithNavController(controller)
        }
    }

}
