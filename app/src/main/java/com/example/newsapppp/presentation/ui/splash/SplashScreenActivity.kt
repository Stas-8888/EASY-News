package com.example.newsapppp.presentation.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.newsapppp.R
import com.example.newsapppp.databinding.ActivitySplashScreenBinding
import com.example.newsapppp.presentation.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    val viewModel by viewModels<SplashActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.setupDayNightMode()
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorRed)
        Log.e("AAA","activitySplash")
        val animationBounce = AnimationUtils.loadAnimation(this, R.anim.bounce)
        binding.tvNews.startAnimation(animationBounce)
        binding.tvWelcome.startAnimation(animationBounce)

        lifecycleScope.launchWhenResumed {
            delay(TimeUnit.SECONDS.toMillis(1))
            toMainActivity()
        }
    }

    private fun toMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
