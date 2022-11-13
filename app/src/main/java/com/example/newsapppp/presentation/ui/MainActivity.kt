package com.example.newsapppp.presentation.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.newsapppp.R
import com.example.newsapppp.databinding.ActivityMainBinding
import com.example.newsapppp.presentation.utils.ConnectionType
import com.example.newsapppp.presentation.utils.NetworkMonitorUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.no_internet_connections.*

/**
 * Representation of SingleActivity. Set Activity UI and navigation
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val networkMonitor = NetworkMonitorUtil(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("AAA","activityMain")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkInternetConnections()
        setBottomNavListener()
        navController = findNavController(R.id.nav_fragment)
    }

    private fun setBottomNavListener() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.mainFragment -> navController.navigate(R.id.mainFragment)
                R.id.saveFragment -> navController.navigate(R.id.saveFragment)
                R.id.searchFragment -> navController.navigate(R.id.searchFragment)
            }
            true
        }
    }

    private fun checkInternetConnections() {
        networkMonitor.result = { isAvailable, type ->
            lifecycleScope.launchWhenResumed {
                when (isAvailable) {
                    true -> {
                        when (type) {
                            ConnectionType.Wifi -> {}
                            ConnectionType.Cellular -> internetConnectionDialog()
                            else -> {}
                        }
                    }
                    false -> internetConnectionDialog()
                }
            }
        }
    }

    private fun internetConnectionDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.no_internet_connections)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.bt_try_again.setOnClickListener {
            ActivityCompat.recreate(this)
        }
        dialog.show()
    }

    override fun onResume() {
        super.onResume()
        networkMonitor.register()
    }

    override fun onStop() {
        super.onStop()
        networkMonitor.unregister()
    }
}
