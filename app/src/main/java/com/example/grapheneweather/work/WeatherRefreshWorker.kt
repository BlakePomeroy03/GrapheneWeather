package com.example.grapheneweather.work

import android.content.Context
import androidx.glance.appwidget.updateAll
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.grapheneweather.data.WeatherRepository
import com.example.grapheneweather.widgets.WeatherWidget

class WeatherRefreshWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val repository = WeatherRepository(applicationContext)
            repository.getCurrentWeather()
            WeatherWidget().updateAll(applicationContext)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}