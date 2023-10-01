package com.aram.weatherapp.models

import com.google.gson.annotations.SerializedName

data class GeoCodeResponseItem(
    val country: String,
    val lat: Double,
    val lon: Double,
    @SerializedName("name")
    val cityName: String,
    val state: String
)