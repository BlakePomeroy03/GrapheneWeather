package com.example.grapheneweather.data

data class WeatherInfo(
    val temperature: String,
    val condition: String,
    val locationLabel: String,
    val high: String,
    val low: String
)