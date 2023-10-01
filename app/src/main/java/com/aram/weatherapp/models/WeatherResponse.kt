package com.aram.weatherapp.models

import com.google.gson.annotations.SerializedName

data class WeatherResponse (
    val main: Main,
    val weather: List<Weather>,
    @SerializedName("name")
    val cityName: String,
)