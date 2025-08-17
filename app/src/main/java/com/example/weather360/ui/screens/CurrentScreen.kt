package com.example.weather360.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weather360.net.OpenWeather
import com.example.weather360.viewmodel.WeatherViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject

@Composable
fun CurrentScreen(viewModel: WeatherViewModel) {
    var city by remember { mutableStateOf(viewModel.currentCity) }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var current by remember { mutableStateOf<JSONObject?>(null) }
    var icon by remember { mutableStateOf<String?>(null) }
    var resolvedName by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(listOf(Color(0xFF2193B0), Color(0xFF6DD5ED))))
            .padding(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            OutlinedTextField(
                value = city,
                onValueChange = {
                    city = it
                    viewModel.currentCity = it // Update ViewModel
                },
                label = { Text("City Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.2f), RoundedCornerShape(12.dp)),
                colors = OutlinedTextFieldDefaults.colors()
            )

            Button(
                onClick = {
                    if (city.isBlank()) return@Button
                    isLoading = true
                    error = null
                    current = null
                    icon = null
                    resolvedName = null
                    scope.launch {
                        val result = OpenWeather.currentByCity(city)
                        result.fold(
                            onSuccess = { json ->
                                current = json
                                resolvedName = listOfNotNull(json.optString("name"), json.optJSONObject("sys")?.optString("country")).joinToString(", ")
                                icon = json.optJSONArray("weather")?.optJSONObject(0)?.optString("icon")
                            },
                            onFailure = { error = it.message }
                        )
                        isLoading = false
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Get Weather") }

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            AnimatedVisibility(visible = !isLoading && (current != null || error != null)) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (error != null) {
                        Text(text = error ?: "", color = Color.Red, fontSize = 14.sp)
                    } else {
                        val temp = current?.optJSONObject("main")?.optDouble("temp")?.let { String.format("%.1f", it) }
                        val desc = current?.optJSONArray("weather")?.optJSONObject(0)?.optString("description")
                        Text(
                            text = resolvedName ?: city,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "${temp ?: "--"}°C - ${desc ?: "--"}",
                            fontSize = 20.sp,
                            color = Color.White
                        )
                        val iconUrl = icon?.let { "https://openweathermap.org/img/wn/${it}@2x.png" }
                        if (iconUrl != null) {
                            AsyncImage(
                                model = iconUrl,
                                contentDescription = "Weather icon",
                                modifier = Modifier.size(80.dp)
                            )
                        }
                        Text(text = "Powered by OpenWeather", fontSize = 12.sp, color = Color.White.copy(alpha = 0.9f))
                    }
                }
            }
        }
    }
}
