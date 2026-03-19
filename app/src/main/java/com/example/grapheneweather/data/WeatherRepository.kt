package com.example.grapheneweather.data

import com.example.grapheneweather.network.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository {

    companion object {
        private var cachedWeather: CachedWeather? = null
    }

    suspend fun getCurrentWeather(): WeatherInfo = withContext(Dispatchers.IO) {
        val response = NetworkModule.weatherApiService.getForecast(
            latitude = 40.01499,
            longitude = -105.27055
        )

        val weather = WeatherInfo(
            temperature = "${response.current.temperature_2m.toInt()}°",
            condition = mapWeatherCode(response.current.weather_code),
            locationLabel = "Boulder",
            high = "H:${response.daily.temperature_2m_max.first().toInt()}°",
            low = "L:${response.daily.temperature_2m_min.first().toInt()}°"
        )

        cachedWeather = CachedWeather(
            temperature = weather.temperature,
            condition = weather.condition,
            locationLabel = weather.locationLabel,
            high = weather.high,
            low = weather.low
        )

        weather
    }

    fun getCachedWeather(): WeatherInfo? {
        return cachedWeather?.toWeatherInfo()
    }

    private fun mapWeatherCode(code: Int): String {
        return when (code) {
            0 -> "Clear"
            1, 2, 3 -> "Partly cloudy"
            45, 48 -> "Fog"
            51, 53, 55 -> "Drizzle"
            61, 63, 65 -> "Rain"
            71, 73, 75 -> "Snow"
            95 -> "Thunderstorm"
            else -> "Unknown"
        }
    }
}