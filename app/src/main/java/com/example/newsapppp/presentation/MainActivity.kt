package com.example.newsapppp.presentation

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.newsapppp.R
import com.example.newsapppp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_splash)

        CoroutineScope(Dispatchers.Main).launch {
            delay(1200)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
            setBottomNavListener()
            navController = Navigation.findNavController(this@MainActivity, R.id.nav_fragment)
            sharedPreferencesGetDayNight()
        }
    }

    private fun sharedPreferencesGetDayNight() {
        sharedPref = getSharedPreferences("Table", Context.MODE_PRIVATE)
        val nightMode = sharedPref.getInt("SWITCH_KEY", 0)

        if (nightMode == 0) {
            AppCompatDelegate
                .setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES
                )
        } else {
            AppCompatDelegate
                .setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                )
        }
    }

    private fun setBottomNavListener() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {

                R.id.mainFragment -> {
                    navController.navigate(R.id.mainFragment)
                }

                R.id.searchNewsFragment -> {
                    navController.navigate(R.id.searchFragment)
                }

                R.id.saveFragment -> {
                    navController.navigate(R.id.saveFragment)
                }
            }
            true
        }
    }
}
