package com.example.grapheneweather.widgets

import android.content.Context
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.Text
import com.example.grapheneweather.data.WeatherInfo

class WeatherWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val weather = WeatherInfo(
            temperature = "72°",
            condition = "Sunny",
            locationLabel = "GrapheneWeather"
        )

        provideContent {
            Column(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(weather.temperature)
                Text(weather.condition)
                Text(weather.locationLabel)
            }
        }
    }
}