package com.example.weather360

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weather360.ui.theme.Weather360Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Weather360Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WeatherScreen(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun WeatherScreen(modifier: Modifier = Modifier) {
    var city by remember { mutableStateOf("") }
    var temp by remember { mutableStateOf<Double?>(null) }
    var desc by remember { mutableStateOf("") }
    var icon by remember { mutableStateOf("") }
    var isVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF2193b0), Color(0xFF6dd5ed))
                )
            )
            .padding(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("City Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.White.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(12.dp)
                    ),
                colors = OutlinedTextFieldDefaults.colors()
            )

            Button(
                onClick = {
                    if (city.isBlank()) return@Button
                    isLoading = true
                    isVisible = false
                    scope.launch(Dispatchers.IO) {
                        try {
                            val apiKey = "6778110d2d3601a7d6d81b60c83c82e7"
                            val apiUrl =
                                "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$apiKey&units=metric"

                            val url = URL(apiUrl)
                            val connection = url.openConnection() as HttpURLConnection
                            connection.connectTimeout = 5000
                            connection.readTimeout = 5000
                            connection.requestMethod = "GET"
                            connection.connect()

                            val responseCode = connection.responseCode
                            val response = connection.inputStream.bufferedReader().readText()

                            if (responseCode == 200) {
                                val json = JSONObject(response)
                                val main = json.getJSONObject("main")
                                val weatherArr = json.getJSONArray("weather").getJSONObject(0)

                                temp = main.getDouble("temp")
                                desc = weatherArr.getString("description")
                                icon = weatherArr.getString("icon")
                            } else {
                                temp = null
                                desc = "Error: HTTP $responseCode"
                            }

                        } catch (e: Exception) {
                            temp = null
                            desc = "Error: ${e.message}"
                        } finally {
                            isVisible = true
                            isLoading = false
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Get Weather")
            }

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = Color.White
                )
            }

            AnimatedVisibility(visible = isVisible && !isLoading) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (temp != null) {
                        Text(
                            text = city,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "${temp}°C - $desc",
                            fontSize = 20.sp,
                            color = Color.White
                        )

                        val iconUrl = "https://openweathermap.org/img/wn/${icon}@2x.png"
                        AsyncImage(
                            model = iconUrl,
                            contentDescription = "Weather icon",
                            modifier = Modifier.size(80.dp)
                        )
                    } else {
                        Text(desc, color = Color.Red)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherPreview() {
    Weather360Theme {
        WeatherScreen()
    }
}
