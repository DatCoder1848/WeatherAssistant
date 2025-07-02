package com.example.weatherassistant

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.weatherassistant.viewmodel.WeatherDataViewModel
import com.example.weatherassistant.ui.theme.WeatherAssistantTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherassistant.UIState.ErrorScreen
import com.example.weatherassistant.UIState.LoadingScreen
import com.example.weatherassistant.UIState.WeatherUIState
import com.example.weatherassistant.views.MainScreen
import com.example.weatherassistant.views.components.DaySwitchingButton


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAssistantTheme {
                val viewModel: WeatherDataViewModel = viewModel()
                val uiState by viewModel.uiState.collectAsState()
                val context = LocalContext.current
                var hasFetched by remember { mutableStateOf(false) }

                // Kiểm tra và đảm bảo được cấp phép truy cập vị trí hiện tại
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        1001
                    )
                }
                // Thực hiện lấy vị trí hiện tại
                LaunchedEffect(Unit) {
                    if(!hasFetched){
                        viewModel.getCurrentLocation(context = context){ location ->
                            if (location != null ){
                                val lat = location.latitude
                                val long = location.longitude
                                viewModel.fetchWeatherFor(location = "$lat,$long")
                            } else{
                                viewModel.setError("Không lấy được vị trí thiết bị")
                                Log.e("CurrentLocation", "❌ Khoong lay dc vi tri hien tai")
                            }
                        }
                        hasFetched = true
                    }
                }

                Column(
                    modifier = Modifier.fillMaxSize()
                ){
                    when(uiState){
                        is WeatherUIState.Success -> {
                            MainScreen(viewModel = viewModel, context = context)
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

@Preview(showBackground = true)
@Composable
fun MainScreenPreview(){

}

@Preview
@Composable
fun ShowDemo(){
    //LoadingScreen()
    //ErrorScreen("404")
    Column(Modifier.fillMaxSize().background(Color.White)) {
       // DaySwitchingButton { }
    }
}