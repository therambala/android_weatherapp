package com.aram.weatherapp.models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class WeatherRepository @Inject constructor (private val weatherApi: WeatherAPI) {

    fun getMainWeatherData(latitude: Double?, longitude:Double?) : MutableLiveData<WeatherResponse?> {
        val mainWeatherLiveData = MutableLiveData<WeatherResponse?>()
        weatherApi.getMainWeatherData(latitude, longitude).enqueue(
            object: Callback<WeatherResponse> {
                override fun onResponse(
                    call: Call<WeatherResponse>,
                    response: Response<WeatherResponse>
                ) {
                    if (response.errorBody() == null && response.isSuccessful){
                        mainWeatherLiveData.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    //TODO Show some error message to user if the call fails
                    mainWeatherLiveData?.postValue(null)
                    Log.d("getMainWeatherData", "onFailure:${t.printStackTrace()} ")
                }

            }
        )
        return mainWeatherLiveData
    }

    fun getGeoCoordinate(cityName:String): MutableLiveData<GeoCodeResponseItem?> {
        val geoCodeResponse = MutableLiveData<GeoCodeResponseItem?>()
        weatherApi.getGeoCoordinates(cityName).enqueue(
            object: Callback<GeoCodeResponse> {
                override fun onResponse(
                    call: Call<GeoCodeResponse>,
                    response: Response<GeoCodeResponse>
                ) {
                    if (response.isSuccessful && response.body()?.isNotEmpty() == true){
                        geoCodeResponse.postValue(response.body()?.first())
                    } else if (response.body()?.isEmpty() == true){
                        geoCodeResponse.postValue(null)
                        Log.d("getGeoCoordinate", "onFailure: Response is empty")
                    }
                }

                override fun onFailure(call: Call<GeoCodeResponse>, t: Throwable) {
                    //TODO Show some error message to user if the call fails
                    geoCodeResponse.postValue(null)
                    Log.d("getGeoCoordinate", "onFailure: ${t.printStackTrace()}")
                }

            }
        )
        return geoCodeResponse
    }
}