package com.example.weatherassistant.viewmodel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.weatherassistant.data.model.WeatherApiResponse
import com.example.weatherassistant.data.model.WeatherDay
import com.example.weatherassistant.data.model.WeatherHour
import com.example.weatherassistant.data.remote.RetrofitInstance
import com.example.weatherassistant.Model.WeatherData
import com.example.weatherassistant.UIState.WeatherUIState
import com.example.weatherassistant.views.components.WhatTheDay
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime


class WeatherDataViewModel(): ViewModel() {
    // Lời thông báo
    private val _notification = MutableStateFlow<String?>(null)
    val notification: StateFlow<String?> = _notification
    // Biến trạng thái của ứng dụng
    private val _uiState = MutableStateFlow<WeatherUIState>(WeatherUIState.Empty)
    val uiState: StateFlow<WeatherUIState> = _uiState
    // Biến lưu dữ liệu gốc lấy về
    private val _wholeResponseData = MutableStateFlow<WeatherApiResponse?>(null)
    val wholeResponseData: StateFlow<WeatherApiResponse?> = _wholeResponseData
    // Tạo biến lưu danh sách của data theo từng ngày để có thể linh hoạt lấy data theo từng ngày
    val listDaysData: List<WeatherDay>
        get() = _wholeResponseData.value?.days ?: emptyList()
    val _currentWeatherData = MutableStateFlow<WeatherData?>(null)
    val currentWeatherData: StateFlow<WeatherData?> = _currentWeatherData

    fun fetchWeatherFor(location: String){
        viewModelScope.launch {
            // Nếu như hiện tại không Success thì có thể bật trạng thái loading -> điều này giúp tránh việc hiện Loading khi không fetch đc data mà lần trước đó success
            if(_uiState.value !is WeatherUIState.Success) _uiState.value = WeatherUIState.Loading

            Log.d("fetchData", "✅ Gọi fetchWeatherFor thành công!!!")
            delay(1500) // Dung lai gia su dang fetch data

            try {
                val apiKey = "JXNAM6RCU3LPJJYCB9SWRBC2L"
                Log.d("fetchData", "✅ Đã lấy được api key rồi !!!")
                val response: WeatherApiResponse = RetrofitInstance.apiService.getTodayWeather(location, key = apiKey )
                // Cập nhật dữ liệu từ response mới:
                _wholeResponseData.value = response
                Log.d("fetchData", "✅ Đã lấy được response !!!")
                _uiState.value = WeatherUIState.Success

            }catch (e: Exception){
                showNotification("❌ Địa điểm bạn nhập không hợp lệ ❌")
                Log.d("fetchData", "❌ Lỗi không fetch được data: ${e.message}", e)

                //_uiState.value = WeatherUIState.Error("$e.message")
            }
        }
    }

    fun getCurrentLocation(context: Context, onLocationReceive: (Location?) -> Unit){
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 1000
            numUpdates = 1
        }

        val locationCallBack = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                val location = p0.lastLocation
                onLocationReceive(location)
                fusedLocationClient.removeLocationUpdates(this)
            }
        }

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            onLocationReceive(null)
            return
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallBack,
            Looper.getMainLooper()
        )
    }

    fun setError(message: String){
        _uiState.value = WeatherUIState.Error(message)
    }

    fun showNotification(message: String?){
        _notification.value = message
        viewModelScope.launch {
            delay(3000)
            _notification.value = null
        }
    }

    fun getTodayIndex(): Int? {
        val today = LocalDate.now()
        for (i in 0..(listDaysData.size - 1) ) {
            if (listDaysData[i].datetime == today.toString())
                return i
        }
        return null
    }

    fun getWeatherDataByDay(dateIndex: Int): WeatherData {
        // Lay du lieu ngay can lay:
        val dayData = listDaysData[dateIndex]

        return WeatherData(
            locationName = _wholeResponseData.value?.address ?: "",
            location = _wholeResponseData.value?.address ?: "",
            condition = dayData.icon,
            currentTemp = dayData.temp,
            maxTemp = dayData.tempmax,
            minTemp = dayData.tempmin,
            dayOfWeek = WhatTheDay(LocalDate.parse(dayData.datetime)),
            date = LocalDate.parse(dayData.datetime),
            humidity = dayData.humidity,
            pressure = dayData.pressure,
            windSpeed = dayData.windspeed,
            sunset = dayData.sunset,
            sunrise = dayData.sunrise,
            uvIndex = dayData.uvindex)
    }

    fun getWeatherDataByHour(dateIndex: Int, hour: Int): WeatherData {
        val dayData = listDaysData[dateIndex]
        val hourData = dayData.hours[hour]

        return WeatherData(
            locationName = _wholeResponseData.value?.address ?: "",
            location = _wholeResponseData.value?.address ?: "",
            condition = hourData.icon,
            currentTemp = hourData.temp,
            maxTemp = dayData.tempmax,
            minTemp = dayData.tempmin,
            dayOfWeek = WhatTheDay(LocalDate.parse(dayData.datetime)),
            date = LocalDate.parse(dayData.datetime),
            humidity = hourData.humidity,
            pressure = hourData.pressure,
            windSpeed = hourData.windspeed,
            sunset = dayData.sunset,
            sunrise = dayData.sunrise,
            uvIndex = hourData.uvindex)
    }

}