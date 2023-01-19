package com.example.newsapppp.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapppp.R
import com.example.newsapppp.databinding.ActivityMainBinding
import com.example.newsapppp.presentation.ui.root.RootFragment
import com.example.newsapppp.presentation.utils.extensions.internetConnectionDialog
import com.muddassir.connection_checker.ConnectionState
import com.muddassir.connection_checker.ConnectivityListener
import com.muddassir.connection_checker.checkConnection
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ConnectivityListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(R.id.container, RootFragment()).commit()
        checkConnection(this)
    }

    override fun onConnectionState(state: ConnectionState) {
        when (state) {
            ConnectionState.CONNECTED -> {
                internetConnectionDialog("Connected")
            }
            ConnectionState.SLOW -> {
                internetConnectionDialog("Slow Internet Connection")
            }
            else -> {
                internetConnectionDialog("Disconnected")
            }
        }
    }
}
