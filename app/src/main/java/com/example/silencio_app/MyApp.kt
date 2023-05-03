package com.example.silencio_app

import android.app.Application

import com.example.silencio_app.util.PrefManagers
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApp:Application() {

    override fun onCreate() {
        super.onCreate()
        PrefManagers.init(this)
    }
}