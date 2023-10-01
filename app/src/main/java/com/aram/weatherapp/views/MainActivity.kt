package com.aram.weatherapp.views

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.aram.weatherapp.AppConstants
import com.aram.weatherapp.WeatherApplication
import com.aram.weatherapp.databinding.ActivityMainBinding
import com.aram.weatherapp.utils.WeatherAppUtils.checkLocationPermission
import com.aram.weatherapp.utils.WeatherAppUtils.getCurrentLocation
import com.aram.weatherapp.utils.WeatherAppUtils.getLastSearchedCity
import com.aram.weatherapp.utils.WeatherAppUtils.hideSoftKeyboard
import com.aram.weatherapp.utils.WeatherAppUtils.loadImage
import com.aram.weatherapp.utils.WeatherAppUtils.requestLocationPermission
import com.aram.weatherapp.utils.WeatherAppUtils.saveLastSearchedCity
import com.aram.weatherapp.viewmodels.WeatherViewModel
import com.aram.weatherapp.viewmodels.WeatherViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var weatherIcon: String

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: WeatherViewModel

    private var lastSearchedCity: String? = null

    private var currentLocation : Location? = null

    @Inject
    lateinit var viewModelFactory: WeatherViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (application as WeatherApplication).appComponent.inject(this)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(modelClass = WeatherViewModel::class.java)
        if(!checkLocationPermission(this)){
            requestLocationPermission(this)
        } else {
            currentLocation = getCurrentLocation(this)
        }
        currentLocation?.let {
            loadWeatherInfo(it.latitude, it.longitude)
        } ?: run {
            lastSearchedCity = getLastSearchedCity(this)
            lastSearchedCity?.let { if(it.isNotBlank()) onGetWeather(it) }
        }
        bindEvents()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            AppConstants.LOCATION_REQUEST_CODE -> {
                currentLocation = if (grantResults.first() == PackageManager.PERMISSION_GRANTED ||
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation(this)
                } else {
                    null
                }
            }
        }
    }

    /*
     * Handle getWeather button click event
     * */
    private fun bindEvents() {
        addTextWatcher()
        binding.btGetWeather.setOnClickListener {
            val cityName = binding.etEnterCity.text.toString()
            saveLastSearchedCity(this, cityName)
            hideSoftKeyboard(this)
            onGetWeather(cityName)
        }
    }


    /*
    * Get geo coordinates (lat and lon) of the given city
    * Use the returned geo coordinates to retrieve relevant
    * weather info
    * */
    private fun onGetWeather(cityName: String) {
        binding.progressBar.visibility = View.VISIBLE

        viewModel.getGeoCoordinates(cityName).observe(
            this
        )
        { geocodeItem ->
            geocodeItem?.let {
                loadWeatherInfo(it.lat, it.lon)
            } ?: run {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this@MainActivity,
                    "Please enter valid city name and/or try again later.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }


    /*
    * Load weather info into views
    * */
    @SuppressLint("SetTextI18n")
    private fun loadWeatherInfo(lat: Double, lon: Double) {
        viewModel.getMainWeatherData(lat, lon).observe(
            this@MainActivity
        ) { weatherData ->
            weatherData?.let {
                weatherIcon = it.weather.first().iconCode
                binding.progressBar.visibility = View.GONE
                binding.cityName.text = "City : ${it.cityName}"
                binding.currentTemp.text = "Current temp : ${it.main.currentTemp}"
                binding.feelsLike.text = "Feels Like : ${it.main.feelsLike}"
                binding.currentCondition.text =
                    "Current condition : ${it.weather.first().currentCondition}"
                binding.highTemp.text = "High : ${it.main.tempMax}"
                binding.lowTemp.text = " Low : ${it.main.tempMin}"
                loadImage(binding.weatherIcon, weatherIcon)
                showDisclaimer()
            } ?: run {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this@MainActivity, "Could not get weather data. Please try again later.", Toast.LENGTH_LONG).show()
            }
        }
    }

    /*
    * Add textWatcher to edit text to listen to user input events
    * */
    private fun addTextWatcher() {
        binding.etEnterCity.addTextChangedListener(
           object : TextWatcher {
               override fun beforeTextChanged(
                   s: CharSequence?,
                   start: Int,
                   count: Int,
                   after: Int
               ) {

               }

               override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                   //enable getWeather button only if text in edit text field is non empty
                   binding.btGetWeather.isEnabled = s?.isNotEmpty() == true
               }

               override fun afterTextChanged(s: Editable?) {

               }

           }
        )
    }

    private fun showDisclaimer(){
        binding.disclaimer.visibility = View.VISIBLE
    }
}