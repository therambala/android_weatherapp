package com.aram.weatherapp.utils

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.aram.weatherapp.AppConstants.BASE_URL_IMAGE
import com.aram.weatherapp.AppConstants.IMAGE_RESOLUTION
import com.aram.weatherapp.AppConstants.LAST_SEARCHED_CITY
import com.aram.weatherapp.AppConstants.LOCATION_REQUEST_CODE
import com.aram.weatherapp.AppConstants.SHARED_PREFERENCES_FILE
import com.bumptech.glide.Glide

object WeatherAppUtils {
    /*
     * Save last searched city to shared preferences
     */
    fun saveLastSearchedCity(context: Context, lastSearchedCity: String){
        val sharedPreferences = context.getSharedPreferences(
            SHARED_PREFERENCES_FILE,
            AppCompatActivity.MODE_PRIVATE
        )
        sharedPreferences.edit().putString(LAST_SEARCHED_CITY, lastSearchedCity).apply()
    }

    /*
    * Get last searched location from shared preferences for the app.
    * */
    fun getLastSearchedCity(context: Context): String? {
        return context.getSharedPreferences(
            SHARED_PREFERENCES_FILE,
            AppCompatActivity.MODE_PRIVATE
        ).getString(LAST_SEARCHED_CITY, null)
    }

    /*
    * Hide soft keyboard
    * */
    fun hideSoftKeyboard(activity: FragmentActivity) {
        val ims = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        ims.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
    }

    /*
    * Load image into image view from given url.
    *
    * Image Resolution is currently set to 2x. It could be calculated based on
    * device screen density
    * */
    fun loadImage(imgView: ImageView, weatherIcon: String){
        Glide.with(imgView.context)
            .load("$BASE_URL_IMAGE$weatherIcon$IMAGE_RESOLUTION")
            .override(500,500)
            .into(imgView)
    }

    fun checkLocationPermission(context: Context) : Boolean {
        return ActivityCompat.checkSelfPermission (
            context.applicationContext,
            ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||

            ActivityCompat.checkSelfPermission (
                context.applicationContext,
                ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    fun requestLocationPermission(activity: FragmentActivity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION),
            LOCATION_REQUEST_CODE
        )
    }

    fun getCurrentLocation(context: Context) : Location? {
        var currentLocation : Location? = null
        when {
            checkLocationPermission(context).not() -> {
                requestLocationPermission(context as FragmentActivity)
            }
            else -> {
                val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val locationListener = LocationListener { location ->  currentLocation = location }
                if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 0f, locationListener)
                    currentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    return currentLocation
                }

            }
        }
        return currentLocation
    }
}