package com.example.weatherassistant.ViewModel

import androidx.lifecycle.ViewModel
import com.example.weatherassistant.Model.WeatherData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

class WeatherDataViewModel(): ViewModel() {
    private var _weatherDataList = MutableStateFlow<List<WeatherData>>(emptyList())
    private var _weatherData = MutableStateFlow<WeatherData?>(null)
    var weatherDataList: StateFlow<List<WeatherData>> = _weatherDataList
    var weatherData: StateFlow<WeatherData?> = _weatherData

    fun createNewData(
         locationName: String,
         condition: String,
         currentTemp: Double,
         maxTemp: Double,
         minTemp: Double,
         dayOfWeek: String,
         date: LocalDate,
         humidity: Int,
         pressure: Int,
         windSpeed: Double,
         sunrise: String,
         sunset: String,
         uvIndex: Double
    ): WeatherData? {
        val newWeatherData = WeatherData(
            locationName = locationName,
            condition = condition,
            currentTemp = currentTemp,
            maxTemp = maxTemp,
            minTemp = minTemp,
            dayOfWeek = dayOfWeek,
            date = date,
            humidity = humidity,
            pressure = pressure,
            windSpeed = windSpeed,
            sunrise = sunrise,
            sunset = sunset,
            uvIndex = uvIndex
        )
        _weatherData.value = newWeatherData
        return _weatherData?.value ?: null
    }

    fun updateData( data: WeatherData ){
        _weatherData.value = data
    }
}