package com.aram.weatherapp.di

import com.aram.weatherapp.AppConstants
import com.aram.weatherapp.models.WeatherAPI
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client( OkHttpClient.Builder().addInterceptor(interceptor).build())
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesWeatherAPI(retrofit: Retrofit): WeatherAPI{
        return retrofit.create(WeatherAPI::class.java)
    }

    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
}