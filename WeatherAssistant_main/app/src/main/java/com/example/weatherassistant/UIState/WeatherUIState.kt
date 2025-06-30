package com.example.weatherassistant.UIState

import com.example.weatherassistant.Model.WeatherData

sealed class WeatherUIState {
    object Loading : WeatherUIState()
    data class Success(val data: WeatherData) : WeatherUIState()
    data class Error(val message: String) : WeatherUIState()
    object Empty : WeatherUIState()
}

