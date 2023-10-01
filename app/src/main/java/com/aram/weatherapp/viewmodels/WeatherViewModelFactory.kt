package com.aram.weatherapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aram.weatherapp.models.WeatherRepository
import javax.inject.Inject

/*
* Need viewModel factory as our view model is parameterized. If the view model is
* non parameterized, then this class is not required.
* */

class WeatherViewModelFactory @Inject constructor (private val repository: WeatherRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(WeatherRepository::class.java).newInstance(repository)
    }
}