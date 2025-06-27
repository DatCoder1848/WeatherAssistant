package com.example.weatherassistant.Model

import java.time.LocalDate

data class WeatherData (
    val locationName: String,
    val condition: String,
    val currentTemp: Double,
    val maxTemp: Double,
    val minTemp: Double,
    val dayOfWeek: String,
    val date: LocalDate,
    val humidity: Int,
    val pressure: Int,
    val windSpeed: Double,
    val sunrise: String,
    val sunset: String,
    val uvIndex: Double
)
