package com.aram.weatherapp.models

import com.aram.weatherapp.BaseUnitTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import retrofit2.Call

class WeatherRepositoryTest : BaseUnitTest() {

    @Mock
    private lateinit var mockAPI : WeatherAPI

    @Mock
    private lateinit var mockWeatherResponseCall : Call<WeatherResponse>

    @Mock
    private lateinit var mockGeoCodeResponseCall : Call<GeoCodeResponse>

    private lateinit var repository: WeatherRepository

    @Before
    override fun setUp() {
        super.setUp()
        repository = WeatherRepository(mockAPI)
    }

    @Test
    fun getMainWeatherData() {
        `when`(mockAPI.getMainWeatherData(mockDouble, mockDouble)).thenReturn(mockWeatherResponseCall)
        repository.getMainWeatherData(mockDouble, mockDouble)
        verify(mockAPI).getMainWeatherData(mockDouble, mockDouble)
    }

    @Test
    fun getGeoCoordinate() {
        `when`(mockAPI.getGeoCoordinates(mockString)).thenReturn(mockGeoCodeResponseCall)
        repository.getGeoCoordinate(mockString)
        verify(mockAPI).getGeoCoordinates(mockString)
    }
}