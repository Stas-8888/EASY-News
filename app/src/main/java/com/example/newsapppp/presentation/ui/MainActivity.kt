package com.example.newsapppp.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapppp.R
import com.example.newsapppp.databinding.ActivityMainBinding
import com.example.newsapppp.presentation.ui.root.RootFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(R.id.container, RootFragment()).commit()
    }
}
