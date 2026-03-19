package com.example.grapheneweather.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import androidx.datastore.preferences.core.MutablePreferences

private val Context.weatherDataStore by preferencesDataStore(name = "weather_cache")

class WeatherStorage(private val context: Context) {

    private object Keys {
        val TEMPERATURE = stringPreferencesKey("temperature")
        val CONDITION = stringPreferencesKey("condition")
        val LOCATION_LABEL = stringPreferencesKey("location_label")
        val HIGH = stringPreferencesKey("high")
        val LOW = stringPreferencesKey("low")
    }

    suspend fun save(weather: WeatherInfo) {
        context.weatherDataStore.edit { prefs: MutablePreferences ->
            prefs[Keys.TEMPERATURE] = weather.temperature
            prefs[Keys.CONDITION] = weather.condition
            prefs[Keys.LOCATION_LABEL] = weather.locationLabel
            prefs[Keys.HIGH] = weather.high
            prefs[Keys.LOW] = weather.low
        }
    }

    suspend fun load(): WeatherInfo? {
        val prefs: Preferences = context.weatherDataStore.data.first()

        val temperature = prefs[Keys.TEMPERATURE] ?: return null
        val condition = prefs[Keys.CONDITION] ?: return null
        val locationLabel = prefs[Keys.LOCATION_LABEL] ?: return null
        val high = prefs[Keys.HIGH] ?: return null
        val low = prefs[Keys.LOW] ?: return null

        return WeatherInfo(
            temperature = temperature,
            condition = condition,
            locationLabel = locationLabel,
            high = high,
            low = low
        )
    }
}