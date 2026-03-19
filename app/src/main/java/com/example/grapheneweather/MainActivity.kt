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
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.grapheneweather.data.WeatherInfo
import com.example.grapheneweather.data.WeatherRepository
import com.example.grapheneweather.ui.theme.GrapheneWeatherTheme
import androidx.glance.appwidget.updateAll
import com.example.grapheneweather.widgets.WeatherWidget

class MainActivity : ComponentActivity() {
    private lateinit var weatherRepository: WeatherRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        weatherRepository = WeatherRepository(applicationContext)

        setContent {
            GrapheneWeatherTheme {
                var weather by remember { mutableStateOf<WeatherInfo?>(null) }
                var errorMessage by remember { mutableStateOf<String?>(null) }

                LaunchedEffect(Unit) {
                    try {
                        weather = weatherRepository.getCurrentWeather()
                        WeatherWidget().updateAll(applicationContext)
                    } catch (e: Exception) {
                        errorMessage = e.message ?: "Unknown error"
                    }
                }

                when {
                    errorMessage != null -> ErrorScreen(errorMessage!!)
                    weather == null -> LoadingScreen()
                    else -> WeatherHomeScreen(weather!!)
                }
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Loading weather...")
    }
}

@Composable
fun ErrorScreen(message: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Weather failed to load",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium
        )
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
        Text(
            text = "${weather.high}  ${weather.low}",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}