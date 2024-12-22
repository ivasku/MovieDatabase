package com.tms.moviedatabase.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MovieApp: Application() {

    companion object {
        const val API_KEY = "123456789123456789"  // Replace with the actual key
    }

    override fun onCreate() {
        super.onCreate()
    }
}