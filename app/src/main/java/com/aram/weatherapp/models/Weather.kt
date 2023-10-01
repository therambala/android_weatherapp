package com.aram.weatherapp.models

import com.google.gson.annotations.SerializedName

data class Weather(
    val id: Int,
    @SerializedName("main")
    val currentCondition: String,
    @SerializedName("description")
    val conditionDescription: String,
    @SerializedName("icon")
    val iconCode: String
)
