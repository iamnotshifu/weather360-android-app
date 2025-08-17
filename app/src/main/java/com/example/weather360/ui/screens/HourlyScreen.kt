package com.example.weather360.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.weather360.net.OpenWeather
import com.example.weather360.viewmodel.WeatherViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HourlyScreen(viewModel: WeatherViewModel) {
    var list by remember { mutableStateOf<List<JSONObject>>(emptyList()) }
    var error by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(viewModel.currentCity) {
        if (viewModel.currentCity.isBlank()) return@LaunchedEffect
        isLoading = true
        error = null
        scope.launch {
            val result = OpenWeather.forecastByCity(viewModel.currentCity)
            result.fold(
                onSuccess = { body ->
                    val arr = body.optJSONArray("list")
                    list = (0 until (arr?.length() ?: 0))
                        .map { arr!!.getJSONObject(it) }
                        .take(8) // 24 hours ÷ 3-hour intervals
                },
                onFailure = { error = it.message }
            )
            isLoading = false
        }
    }

    val sdf = remember { SimpleDateFormat("ha", Locale.getDefault()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(listOf(Color(0xFF2193B0), Color(0xFF6DD5ED))))
            .padding(16.dp)
    ) {
        when {
            isLoading -> Text("Loading hourly…", color = Color.White)
            error != null -> Text(error ?: "", color = Color.Red)
            else -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    list.forEach { forecast ->
                        val ts = forecast.optLong("dt") * 1000
                        val temp = forecast.optJSONObject("main")?.optDouble("temp")
                        val icon = forecast.optJSONArray("weather")?.optJSONObject(0)?.optString("icon")
                        Card {
                            Column(Modifier.padding(12.dp)) {
                                Text(sdf.format(Date(ts)), style = MaterialTheme.typography.titleSmall)
                                AsyncImage(
                                    model = "https://openweathermap.org/img/wn/${icon}@2x.png",
                                    contentDescription = null,
                                    modifier = Modifier.size(48.dp)
                                )
                                Text("${String.format("%.0f", temp ?: 0.0)}°")
                            }
                        }
                    }
                }
            }
        }
    }
}
