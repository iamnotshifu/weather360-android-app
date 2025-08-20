package com.example.weather360.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CreditsScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text("Weather360 - Group 11 - CIS", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Text("Real-time weather forecasts worldwide with Kotlin & OpenWeatherMap API", style = MaterialTheme.typography.bodyMedium)

        Text("Team", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Text("• Shifu-Nfor Nyuiring-Yoh Rhagninyui (21/3135) - Lead, Integration: Managed merges, API issues, Screens")
        Text("• Sunday Daramfon Emmanuel (22/1971) - Current Screen: API error handling, UI layout")
        Text("• Talubi Samuel O. (22/1420) - Hourly Screen: Scroll UI, forecast parsing")
        Text("• Oluwafemi Daramola Olawunmi - Daily Screen: Data grouping, date formatting")
        Text("• Michael Jasper (22/3076) - API: Network calls, key setup")
        Text("• Yisa Mordecai (22/1890) - Settings Screen: API key UI, placeholder units")
        Text("• Adeleke Oluwakayode James - Credits Screen: Layout, team list")
        Text("• Ojo Ayomide Samuel (22/1829) - Navigation: Bottom bar, route setup")
        Text("• Odedele Segun (21/1850) - Main Activity: App init, ViewModel integration")

        Text("Tech", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Text("• OpenWeatherMap API 2.5")
        Text("• Jetpack Compose, Material 3")
        Text("• Coil for icons")
    }
}