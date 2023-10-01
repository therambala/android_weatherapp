package com.aram.weatherapp.models

import com.aram.weatherapp.AppConstants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    /*
    * Retrieving the weather data in Fahrenheit
    * */
    @GET("data/2.5/weather")
    fun getMainWeatherData (
        @Query("lat") latitude:Double?,
        @Query("lon") longitude: Double?,
        @Query("appid") apikey: String = AppConstants.API_KEY,
        @Query("units") unit: String = AppConstants.UNITS_IMPERIAL
    ) : Call<WeatherResponse>


    /*
    * Limiting the city result to just 1.
    * */
    @GET("geo/1.0/direct")
    fun getGeoCoordinates (
        @Query("q") cityName: String,
        @Query("limit") limit: Int = AppConstants.LIMIT,
        @Query("appid") apikey: String = AppConstants.API_KEY
    ) : Call <GeoCodeResponse>
}
