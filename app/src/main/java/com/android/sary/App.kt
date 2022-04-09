package com.android.sary

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class App: Application(), LifecycleObserver, Configuration.Provider  {


    @Inject
    lateinit var workerFactory: HiltWorkerFactory


    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()

        instance = this
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }


    companion object {
        private lateinit var instance: App
    }
}