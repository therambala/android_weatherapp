package com.aram.weatherapp

import android.app.Application
import com.aram.weatherapp.di.AppComponent
import com.aram.weatherapp.di.DaggerAppComponent

class WeatherApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().build()
    }
}