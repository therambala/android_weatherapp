package com.aram.weatherapp.models

import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("temp")
    val currentTemp: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    @SerializedName("temp_min")
    val tempMin: Double
)
