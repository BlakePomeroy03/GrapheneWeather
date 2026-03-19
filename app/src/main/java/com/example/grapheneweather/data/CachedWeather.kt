package com.example.grapheneweather.data

data class CachedWeather(
    val temperature: String,
    val condition: String,
    val locationLabel: String,
    val high: String,
    val low: String
) {
    fun toWeatherInfo(): WeatherInfo {
        return WeatherInfo(
            temperature = temperature,
            condition = condition,
            locationLabel = locationLabel,
            high = high,
            low = low
        )
    }
}