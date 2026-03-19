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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.glance.appwidget.updateAll
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.grapheneweather.data.WeatherInfo
import com.example.grapheneweather.data.WeatherRepository
import com.example.grapheneweather.ui.theme.GrapheneWeatherTheme
import com.example.grapheneweather.widgets.WeatherWidget
import com.example.grapheneweather.work.WeatherRefreshWorker
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    private lateinit var weatherRepository: WeatherRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        weatherRepository = WeatherRepository(applicationContext)
        scheduleWeatherRefresh()
        val appContext = applicationContext

        setContent {
            GrapheneWeatherTheme {
                var weather by remember { mutableStateOf<WeatherInfo?>(null) }
                var errorMessage by remember { mutableStateOf<String?>(null) }

                LaunchedEffect(Unit) {
                    try {
                        weather = weatherRepository.getCurrentWeather()
                        WeatherWidget().updateAll(appContext)
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

    private fun scheduleWeatherRefresh() {
        val request = PeriodicWorkRequestBuilder<WeatherRefreshWorker>(
            15, TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "weather_refresh",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
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