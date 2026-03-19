package com.example.grapheneweather.widgets

import android.content.Context
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.example.grapheneweather.data.WeatherInfo

class WeatherWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val weather = WeatherInfo(
            temperature = "72°",
            condition = "Sunny",
            locationLabel = "Denver"
        )

        provideContent {
            Column(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = weather.locationLabel,
                    style = TextStyle(
                        fontWeight = FontWeight.Normal
                    )
                )

                Spacer(modifier = GlanceModifier.height(8.dp))

                Text(
                    text = weather.temperature,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = GlanceModifier.height(4.dp))

                Text(
                    text = weather.condition
                )
            }
        }
    }
}