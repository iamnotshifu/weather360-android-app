package com.example.weather360.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
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
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DailyScreen(viewModel: WeatherViewModel) {
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
                    val forecasts = (0 until (arr?.length() ?: 0)).map { arr!!.getJSONObject(it) }
                    // Group by day
                    val daily = forecasts.groupBy { forecast ->
                        val ts = forecast.optLong("dt") * 1000
                        val calendar = Calendar.getInstance().apply { timeInMillis = ts }
                        calendar.set(Calendar.HOUR_OF_DAY, 0)
                        calendar.set(Calendar.MINUTE, 0)
                        calendar.set(Calendar.SECOND, 0)
                        calendar.set(Calendar.MILLISECOND, 0)
                        calendar.timeInMillis
                    }.map { (dayTs, dayForecasts) ->
                        val minTemp = dayForecasts.minOfOrNull { it.optJSONObject("main")?.optDouble("temp_min") ?: Double.MAX_VALUE } ?: 0.0
                        val maxTemp = dayForecasts.maxOfOrNull { it.optJSONObject("main")?.optDouble("temp_max") ?: Double.MIN_VALUE } ?: 0.0
                        val midday = dayForecasts.minByOrNull { forecast ->
                            val ts = forecast.optLong("dt") * 1000
                            val cal = Calendar.getInstance().apply { timeInMillis = ts }
                            val hour = cal.get(Calendar.HOUR_OF_DAY)
                            kotlin.math.abs(hour - 12)
                        }
                        JSONObject().apply {
                            put("dt", dayTs / 1000)
                            put("temp", JSONObject().apply {
                                put("min", minTemp)
                                put("max", maxTemp)
                            })
                            put("weather", midday?.optJSONArray("weather") ?: JSONArray())
                        }
                    }
                    list = daily.take(7)
                },
                onFailure = { error = it.message }
            )
            isLoading = false
        }
    }

    val sdf = remember { SimpleDateFormat("EEE, MMM d", Locale.getDefault()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(listOf(Color(0xFF2193B0), Color(0xFF6DD5ED))))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        when {
            isLoading -> Text("Loading daily…", color = Color.White)
            error != null -> Text(error ?: "", color = Color.Red)
            else -> {
                list.forEach { day ->
                    val ts = day.optLong("dt") * 1000
                    val tempObj = day.optJSONObject("temp")
                    val min = tempObj?.optDouble("min")
                    val max = tempObj?.optDouble("max")
                    val icon = day.optJSONArray("weather")?.optJSONObject(0)?.optString("icon")
                    Card {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(sdf.format(Date(ts)))
                                Text("${String.format("%.0f", min ?: 0.0)}° / ${String.format("%.0f", max ?: 0.0)}°")
                            }
                            AsyncImage(
                                model = "https://openweathermap.org/img/wn/${icon}@2x.png",
                                contentDescription = null,
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}