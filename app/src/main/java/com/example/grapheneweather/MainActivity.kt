package com.example.grapheneweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.grapheneweather.data.WeatherInfo
import com.example.grapheneweather.data.WeatherRepository
import com.example.grapheneweather.ui.theme.GrapheneWeatherTheme

class MainActivity : ComponentActivity() {
    private val weatherRepository = WeatherRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val weather = weatherRepository.getCurrentWeather()

        setContent {
            GrapheneWeatherTheme {
                WeatherHomeScreen(weather = weather)
            }
        }
    }
}

@Composable
fun WeatherHomeScreen(weather: WeatherInfo) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = weather.locationLabel,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = weather.temperature,
            style = MaterialTheme.typography.displayLarge
        )
        Text(
            text = weather.condition,
            style = MaterialTheme.typography.titleLarge
        )
    }
}