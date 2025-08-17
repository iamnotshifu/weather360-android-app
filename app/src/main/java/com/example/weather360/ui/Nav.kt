package com.example.weather360.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.weather360.R
import com.example.weather360.ui.screens.CreditsScreen
import com.example.weather360.ui.screens.CurrentScreen
import com.example.weather360.ui.screens.DailyScreen
import com.example.weather360.ui.screens.HourlyScreen
import com.example.weather360.ui.screens.SettingsScreen
import com.example.weather360.viewmodel.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScaffold(
    navController: NavHostController,
    viewModel: WeatherViewModel,
) {
    Scaffold(
        topBar = {
            // Enhanced Top App Bar with gradient background
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)),
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF2196F3), // Blue
                                    Color(0xFF21CBF3), // Light Blue
                                    Color(0xFF03DAC6)  // Teal
                                )
                            )
                        )
                        .padding(vertical = 16.dp, horizontal = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        // Logo with enhanced styling
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    Color.White.copy(alpha = 0.2f)
                                )
                                .padding(8.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_logo),
                                contentDescription = "Logo",
                                tint = Color.White,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        // Enhanced title
                        Column {
                            Text(
                                text = "Weather360",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "Your Weather Companion",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }
                    }
                }
            }
        },
        bottomBar = {
            // Enhanced Bottom Navigation with modern glass effect
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .shadow(12.dp, RoundedCornerShape(24.dp)),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
                )
            ) {
                NavigationBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f)
                                )
                            ),
                            shape = RoundedCornerShape(24.dp)
                        ),
                    containerColor = Color.Transparent,
                    tonalElevation = 0.dp
                ) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    bottomDestinations.forEach { screen ->
                        val isSelected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

                        NavigationBarItem(
                            icon = {
                                Box(
                                    modifier = Modifier
                                        .size(if (isSelected) 32.dp else 28.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(
                                            if (isSelected) {
                                                Brush.radialGradient(
                                                    colors = listOf(
                                                        Color(0xFF2196F3).copy(alpha = 0.2f),
                                                        Color(0xFF03DAC6).copy(alpha = 0.1f)
                                                    )
                                                )
                                            } else {
                                                Brush.radialGradient(
                                                    colors = listOf(Color.Transparent, Color.Transparent)
                                                )
                                            }
                                        )
                                        .padding(4.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = screen.icon,
                                        contentDescription = screen.title,
                                        tint = if (isSelected) {
                                            Color(0xFF2196F3)
                                        } else {
                                            MaterialTheme.colorScheme.onSurfaceVariant
                                        },
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            },
                            label = {
                                Text(
                                    text = screen.title,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    ),
                                    color = if (isSelected) {
                                        Color(0xFF2196F3)
                                    } else {
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                    }
                                )
                            },
                            selected = isSelected,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFF2196F3),
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                selectedTextColor = Color(0xFF2196F3),
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                indicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        // Content area with subtle background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
                        )
                    )
                )
                .padding(innerPadding)
        ) {
            NavHost(
                navController = navController,
                startDestination = Screen.Current.route
            ) {
                composable(Screen.Current.route) { CurrentScreen(viewModel) }
                composable(Screen.Hourly.route) { HourlyScreen(viewModel) }
                composable(Screen.Daily.route) { DailyScreen(viewModel) }
                composable(Screen.Settings.route) { SettingsScreen() }
                composable(Screen.Credits.route) { CreditsScreen() }
            }
        }
    }
}

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    data object Current : Screen("current", "Current", Icons.Filled.Cloud)
    data object Hourly : Screen("hourly", "Hourly", Icons.Filled.Schedule)
    data object Daily : Screen("daily", "Daily", Icons.Filled.CalendarToday)
    data object Settings : Screen("settings", "Settings", Icons.Filled.Settings)
    data object Credits : Screen("credits", "Credits", Icons.Filled.Info)
}

val bottomDestinations = listOf(
    Screen.Current, Screen.Hourly, Screen.Daily, Screen.Settings, Screen.Credits
)