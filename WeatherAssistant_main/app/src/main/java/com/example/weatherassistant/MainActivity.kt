package com.example.weatherassistant

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherassistant.Model.WeatherData
import com.example.weatherassistant.viewmodel.WeatherDataViewModel
import com.example.weatherassistant.ui.theme.WeatherAssistantTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherassistant.UIState.ErrorScreen
import com.example.weatherassistant.UIState.LoadingScreen
import com.example.weatherassistant.UIState.WeatherUIState
import com.example.weatherassistant.views.components.DateContainer
import com.example.weatherassistant.views.components.DetailInfo
import com.example.weatherassistant.views.components.LocationButton
import com.example.weatherassistant.views.components.MainInfo
import com.example.weatherassistant.views.components.SearchBar
import com.example.weatherassistant.views.components.parseResIdFromTitle


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAssistantTheme {
                val viewModel: WeatherDataViewModel = viewModel()
                val uiState by viewModel.uiState.collectAsState()
                val mainBackground = null
                val context = LocalContext.current
                Column(
                    modifier = Modifier.fillMaxSize()
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .paint(
                                painter = painterResource(
                                    id = mainBackground ?: R.drawable.bg_clear_day
                                ),
                                contentScale = ContentScale.Crop
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        SearchBar() { location ->  viewModel.fetchWeatherFor(location) }
                        when(uiState){
                            is WeatherUIState.Success -> {
                                val data = (uiState as WeatherUIState.Success).data
                                MainScreen(data = data, viewModel = viewModel, context = context)
                            }
                            is WeatherUIState.Loading -> { LoadingScreen() }
                            is WeatherUIState.Empty -> { Column(Modifier.fillMaxSize().background(color = Color.White)){}}
                            is WeatherUIState.Error -> { ErrorScreen("Failed")}
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun MainScreen(data: WeatherData, viewModel: WeatherDataViewModel = viewModel(), context: Context){
    val uiState by viewModel.uiState.collectAsState()
    /*= WeatherData(locationName = "", condition = "Clear night", currentTemp = 24.0, maxTemp = 30.0, minTemp = 20.0,
        dayOfWeek = "Thu 6", date = LocalDate.parse("2025-06-27"), humidity = 10, pressure = 5, windSpeed = 111.1, sunset = "5:00", sunrise = "18:51 ", uvIndex = 2.56)*/
    var mainBackground = parseResIdFromTitle(context = context, title = data.condition, prefix = "bg_", oldSeparatedChar = '-', newSeparatedChar = '_')
    //Main container:
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = mainBackground),
                contentScale = ContentScale.Crop
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar() { location ->  viewModel.fetchWeatherFor(location) }
        LocationButton(location = data.locationName, onClick = {})
        MainInfo(condition = "rain-snow-showers-night", date = data.date, temp = 28.0, maxTemp = 31.7, minTemp = 25.0)
        DateContainer(date = data.date)

        Spacer(Modifier.height(30.dp))

        DetailInfo(data)
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview(){
 /*   val data = WeatherData(locationName = "", condition = "Clear night", currentTemp = 24.0, maxTemp = 30.0, minTemp = 20.0,
        dayOfWeek = "Thu 6", date = LocalDate.parse("2004-05-10"), humidity = 10, pressure = 5, windSpeed = 111.1, sunset = "5:00", sunrise = "18:51 ", uvIndex = 2.56)
    MainScreen(data = data)*/
}

@Preview
@Composable
fun ShowDemo(){
    /*val data = WeatherData(locationName = "", condition = "clear night", currentTemp = 24.0, maxTemp = 30.0, minTemp = 20.0,
        dayOfWeek = "Thu 6", date = LocalDate.parse("2025-06-26"), humidity = 10, pressure = 5, windSpeed = 111.1, sunset = "5:00", sunrise = "18:51 ", uvIndex = 2.56)

    Column(
        modifier = Modifier.fillMaxSize()
        ) {
        SearchBar(location = data.locationName, onLocationChange = {})
        LocationButton(location = data.locationName, onClick = {})
        MainInfo(condition = "rain-snow-showers-night", date = data.date, temp = 28.0, maxTemp = 31.7, minTemp = 25.0)
        DateContainer(date = data.date)

        DetailInfo(data)
    }*/
    LoadingScreen()
    ErrorScreen("404")
}