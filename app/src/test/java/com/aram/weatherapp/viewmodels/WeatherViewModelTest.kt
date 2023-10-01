package com.aram.weatherapp.viewmodels

import com.aram.weatherapp.BaseUnitTest
import com.aram.weatherapp.models.WeatherRepository

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class WeatherViewModelTest : BaseUnitTest() {

    @Mock
    private lateinit var mockRepository: WeatherRepository
    private lateinit var viewModel: WeatherViewModel
    @Before
    override fun setUp() {
        super.setUp()
        viewModel = WeatherViewModel(mockRepository)
    }

    @Test
    fun getMainWeatherData() {
        viewModel.getMainWeatherData(mockDouble, mockDouble)
        Mockito.verify(mockRepository).getMainWeatherData(mockDouble, mockDouble)
    }

    @Test
    fun getGeoCoordinates() {
        viewModel.getGeoCoordinates(mockString)
        Mockito.verify(mockRepository).getGeoCoordinate(mockString)
    }
}