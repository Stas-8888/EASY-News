package com.example.newsapppp.presentation.screens

import android.app.Application
import androidx.work.*
import com.example.newsapppp.core.notification.NotificationWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit

/**
 * This class initialize anything that needs to be activated from application start.
 */
@HiltAndroidApp
class CurrencyApp : Application() {

    /**
     * Overrides the onCreate method of the Application class to initialize the app.
     * Calls notificationWork to schedule a periodic notification worker to run every 15 minutes.
     */
    override fun onCreate() {
        super.onCreate()
        notificationWork()
    }

    /**
     * Configures and schedules a periodic notification worker to run every 15 minutes.
     */
    private fun notificationWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()

        val myRequest = PeriodicWorkRequest.Builder(
            NotificationWorker::class.java,
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
}

