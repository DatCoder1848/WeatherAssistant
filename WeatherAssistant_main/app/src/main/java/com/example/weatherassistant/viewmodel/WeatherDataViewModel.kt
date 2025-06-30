package com.example.weatherassistant.viewmodel
//❌ ✅ 🔥 👉 🧠  🔁
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherassistant.data.model.WeatherApiResponse
import com.example.weatherassistant.data.model.WeatherDay
import com.example.weatherassistant.data.model.WeatherHour
import com.example.weatherassistant.data.remote.RetrofitInstance
import com.example.weatherassistant.Model.WeatherData
import com.example.weatherassistant.UIState.WeatherUIState
import com.example.weatherassistant.views.components.WhatTheDay
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate


class WeatherDataViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow<WeatherUIState>(WeatherUIState.Empty)
    val uiState: StateFlow<WeatherUIState> = _uiState

    fun fetchWeatherFor(location: String){
        viewModelScope.launch {
            _uiState.value = WeatherUIState.Loading
            Log.d("fetchData", "✅ Gọi fetchWeatherFor thành công!!!")
            delay(1500) // Dung lai gia su dang fetch data

            try {
                val apiKey = "JXNAM6RCU3LPJJYCB9SWRBC2L"
                Log.d("fetchData", "✅ Đã lấy được api key rồi !!!")
                val response: WeatherApiResponse = RetrofitInstance.apiService.getTodayWeather(location, key = apiKey )
                Log.d("fetchData", "✅ Đã lấy được response !!!")
                val daysData: WeatherDay = response.days.first()
                val hourData: WeatherHour = daysData.hours.first()
                val data = WeatherData(locationName = location,
                    condition = hourData.icon,
                    currentTemp = hourData.temp,
                    maxTemp = hourData.tempmax,
                    minTemp = hourData.tempmin,
                    dayOfWeek = WhatTheDay(LocalDate.parse(daysData.datetime)),
                    date = LocalDate.parse(daysData.datetime),
                    humidity = hourData.humidity,
                    pressure = hourData.pressure,
                    windSpeed = hourData.windspeed,
                    sunset = daysData.sunset,
                    sunrise = daysData.sunrise,
                    uvIndex = hourData.uvindex)
                Log.d("fetchData", "✅ Đã tạo dữ liệu đầy đủ !!!")

                _uiState.value = WeatherUIState.Success(data)
            }catch (e: Exception){
                Log.d("fetchData", "❌ Lỗi không fetch được data: ${e.message}", e)
                _uiState.value = WeatherUIState.Error("$e.message")
            }
        }
    }

    private var _weatherDataList = MutableStateFlow<List<WeatherData>>(emptyList())
    private var _weatherData = MutableStateFlow<WeatherData?>(null)
    var weatherDataList: StateFlow<List<WeatherData>> = _weatherDataList
    var weatherData: StateFlow<WeatherData?> = _weatherData

/*    fun createNewData(
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
    }*/
}