package com.aram.weatherapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aram.weatherapp.models.GeoCodeResponseItem
import com.aram.weatherapp.models.WeatherRepository
import com.aram.weatherapp.models.WeatherResponse

/*
* ViewModel to get weather info and communicate it to views.
* */
class WeatherViewModel (private val repository: WeatherRepository) : ViewModel() {
    fun getMainWeatherData(lat: Double?, lon: Double): MutableLiveData<WeatherResponse?> {
        return repository.getMainWeatherData(lat, lon)
    }

    fun getGeoCoordinates(cityName: String): MutableLiveData<GeoCodeResponseItem?> {
        return repository.getGeoCoordinate(cityName)
    }
}