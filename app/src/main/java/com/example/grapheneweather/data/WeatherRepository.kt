package com.example.grapheneweather.data

class WeatherRepository {
    fun getCurrentWeather(): WeatherInfo {
        return WeatherInfo(
            temperature = "72°",
            condition = "Sunny",
            locationLabel = "Denver"
        )
    }
}