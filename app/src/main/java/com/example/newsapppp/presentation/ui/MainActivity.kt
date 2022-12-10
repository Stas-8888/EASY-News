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
import androidx.work.*
import com.example.newsapppp.R
import com.example.newsapppp.databinding.ActivityMainBinding
import com.example.newsapppp.presentation.ui.root.RootFragment
import com.example.newsapppp.presentation.utils.ConnectionType
import com.example.newsapppp.presentation.utils.MyWorker
import com.example.newsapppp.presentation.utils.NetworkMonitorUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.no_internet_connections.*
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val networkMonitor = NetworkMonitorUtil(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("AAA", "activityMain")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkInternetConnections()
//        setBottomNavListener()
//        navController = findNavController(R.id.nav_fragment)
//        navController.navigate(R.id.rootFragment)

        supportFragmentManager.beginTransaction().replace(R.id.nav_fragment,RootFragment()).commit()

        myPeriodicWork()
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
        val dialog = Dialog(this).apply {
            setContentView(R.layout.no_internet_connections)
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCanceledOnTouchOutside(false)
        }
        bt_try_again.setOnClickListener {
            ActivityCompat.recreate(this)
        }
        dialog.show()
    }

    private fun myPeriodicWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()

        val myRequest = PeriodicWorkRequest.Builder(
            MyWorker::class.java,
            15,
            TimeUnit.MINUTES
        ).setConstraints(constraints)
            .addTag("my_id")
            .build()

        //minimum interval is 15min, just wait 15 min,
        // I will cut this.. to show you
        //quickly

        //now is 0:15 let's wait until 0:30min

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                "my_id",
                ExistingPeriodicWorkPolicy.KEEP,
                myRequest
            )
    }


    private fun myOneTimeWork() {
        val constraints: Constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(true)
            .build()

        val myWorkRequest: WorkRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueue(myWorkRequest)
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
