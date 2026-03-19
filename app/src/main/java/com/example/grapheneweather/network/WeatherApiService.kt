package com.example.grapheneweather.network

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("v1/forecast")
    suspend fun getForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String = "temperature_2m,apparent_temperature,weather_code",
        @Query("daily") daily: String = "temperature_2m_max,temperature_2m_min",
        @Query("temperature_unit") temperatureUnit: String = "fahrenheit",
        @Query("timezone") timezone: String = "auto"
    ): OpenMeteoResponse
}