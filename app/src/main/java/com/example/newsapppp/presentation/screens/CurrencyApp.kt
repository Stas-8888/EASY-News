package com.example.newsapppp.presentation.screens

import android.app.Application
import androidx.work.*
import com.example.newsapppp.presentation.notification.NotificationWorker
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import java.util.concurrent.TimeUnit


private const val REPEAT_INTERVAL: Long = 15
private const val UNIQUE_WORK_NAME = "my_id"

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
        setupTimber()
        notificationWork()
    }

    private fun setupTimber() {
        val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(true) // (Optional) Whether to show thread info or not. Default true
            .methodCount(1) // (Optional) How many method line to show. Default 2
            .methodOffset(5) // Set methodOffset to 5 in order to hide internal method calls
            .tag("LOGGER") // To replace the default PRETTY_LOGGER tag with a dash (-).
            .build()

        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))

        Timber.plant(object : Timber.DebugTree() {
            override fun log(
                priority: Int, tag: String?, message: String, t: Throwable?
            ) {
                Logger.log(priority, "-$tag", message, t)
            }
        })
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
            REPEAT_INTERVAL,
            TimeUnit.MINUTES
        ).setConstraints(constraints)
            .addTag(UNIQUE_WORK_NAME)
            .build()

        //minimum interval is 15min, just wait 15 min,
        // I will cut this.. to show you
        //quickly

        //now is 0:15 let's wait until 0:30min

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                UNIQUE_WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                myRequest
            )
    }
}

