package com.example.newsapppp.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.newsapppp.R
import com.example.newsapppp.databinding.ActivityMainBinding
import com.example.newsapppp.domain.repository.SharedPrefRepository
import com.example.newsapppp.domain.usecase.GetSwitchPositionUseCase
import com.example.newsapppp.presentation.fragments.main.MainFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
//    val viewModel by viewModels<MainFragmentViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.fragment_splash)

//        CoroutineScope(Dispatchers.Main).launch {
//            delay(1200)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
            setBottomNavListener()
            navController = Navigation.findNavController(this@MainActivity, R.id.nav_fragment)
//            sharedPreferencesGetDayNight()

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
//    private fun sharedPreferencesGetDayNight() {
//        val nightMode = viewModel.getSwitchPosition()
//        if (nightMode) {
//            AppCompatDelegate
//                .setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
//        } else {
//            AppCompatDelegate
//                .setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//        }
//    }
}
