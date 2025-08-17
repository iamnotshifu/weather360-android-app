package com.example.weather360.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weather360.BuildConfig

@Composable
fun SettingsScreen() {
    val hasKey = BuildConfig.WEATHER_API_KEY.isNotBlank()
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Settings", style = MaterialTheme.typography.headlineSmall)
        ElevatedCard {
            Column(
                Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text("OpenWeather API Key", style = MaterialTheme.typography.titleMedium)
                Text(
                    text = if (hasKey) "Configured ✅" else "Missing ❌",
                    color = if (hasKey) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                )
                Text(
                    "Set WEATHER_API_KEY in local.properties",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        ElevatedCard {
            Column(
                Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text("Temperature Units", style = MaterialTheme.typography.titleMedium)
                Text("Current: Metric (°C) - Placeholder")
                Text(
                    "Unit toggle (Celsius/Fahrenheit) to be implemented",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}