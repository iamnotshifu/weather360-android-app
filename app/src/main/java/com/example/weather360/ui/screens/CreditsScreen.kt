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
        Text("Weather360 - Group 11", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Text("Real-time weather forecasts worldwide with Kotlin & OpenWeatherMap API", style = MaterialTheme.typography.bodyMedium)

        Text("Team", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Text("• Shifu-Nfor Nyuiring-Yoh Rhagninyui - Lead, Integration: Managed merges, API issues")
        Text("• Sunday Daramfon - Current Screen: API error handling, UI layout")
        Text("• Talubi Samuel O. - Hourly Screen: Scroll UI, forecast parsing")
        Text("• Oluwafemi Daramola - Daily Screen: Data grouping, date formatting")
        Text("• Olawunmi - ViewModel: State management, city sync")
        Text("• Michael Jasper - API: Network calls, key setup")
        Text("• Yisa Mordecai - Settings Screen: API key UI, placeholder units")
        Text("• Adeleke Oluwakayode - Credits Screen: Layout, team list")
        Text("• James - Navigation: Bottom bar, route setup")
        Text("• Odedele Segun - Main Activity: App init, ViewModel integration")

        Text("Tech", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Text("• OpenWeatherMap API 2.5")
        Text("• Jetpack Compose, Material 3")
        Text("• Coil for icons")
    }
}