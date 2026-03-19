package com.example.grapheneweather.network

data class OpenMeteoResponse(
    val current: CurrentWeatherDto,
    val daily: DailyWeatherDto
)

data class CurrentWeatherDto(
    val temperature_2m: Double,
    val weather_code: Int
)

data class DailyWeatherDto(
    val temperature_2m_max: List<Double>,
    val temperature_2m_min: List<Double>
)