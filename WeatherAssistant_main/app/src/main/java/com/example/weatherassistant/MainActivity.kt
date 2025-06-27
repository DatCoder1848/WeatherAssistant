package com.example.weatherassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherassistant.Model.WeatherData
import com.example.weatherassistant.ViewModel.WeatherDataViewModel
import com.example.weatherassistant.ui.theme.WeatherAssistantTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherassistant.View.Components.BackgroundContainer
import com.example.weatherassistant.View.Components.DateContainer
import com.example.weatherassistant.View.Components.DetailInfo
import com.example.weatherassistant.View.Components.LocationButton
import com.example.weatherassistant.View.Components.MainInfo
import com.example.weatherassistant.View.Components.SearchBar
import java.time.LocalDate


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAssistantTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun MainScreen(){
    var viewModel: WeatherDataViewModel = viewModel()
    val data = WeatherData(locationName = "", condition = "Clear night", currentTemp = 24.0, maxTemp = 30.0, minTemp = 20.0,
        dayOfWeek = "Thu 6", date = LocalDate.parse("2025-06-27"), humidity = 10, pressure = 5, windSpeed = 111.1, sunset = "5:00", sunrise = "18:51 ", uvIndex = 2.56)
    var mainBackground by remember { mutableStateOf(R.drawable.bg_clear_night) }
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
        SearchBar(location = data.locationName, onLocationChange = {})
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
    MainScreen()
}

@Preview
@Composable
fun ShowDemo(){
    val data = WeatherData(locationName = "", condition = "clear night", currentTemp = 24.0, maxTemp = 30.0, minTemp = 20.0,
        dayOfWeek = "Thu 6", date = LocalDate.parse("2025-06-26"), humidity = 10, pressure = 5, windSpeed = 111.1, sunset = "5:00", sunrise = "18:51 ", uvIndex = 2.56)

    Column(
        modifier = Modifier.fillMaxSize()
        ) {
        SearchBar(location = data.locationName, onLocationChange = {})
        LocationButton(location = data.locationName, onClick = {})
        MainInfo(condition = "rain-snow-showers-night", date = data.date, temp = 28.0, maxTemp = 31.7, minTemp = 25.0)
        DateContainer(date = data.date)

        DetailInfo(data)
    }
}