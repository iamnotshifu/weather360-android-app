//package com.example.weather360
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.runtime.Composable
//import androidx.navigation.compose.rememberNavController
//import com.example.weather360.ui.WeatherScaffold
//import com.example.weather360.ui.theme.Weather360Theme
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent { AppRoot() }
//    }
//}
//
//@Composable
//fun AppRoot() {
//    Weather360Theme {
//        val navController = rememberNavController()
//        WeatherScaffold(navController = navController)
//    }
//}



package com.example.weather360

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.weather360.ui.WeatherScaffold
import com.example.weather360.ui.theme.Weather360Theme
import com.example.weather360.viewmodel.WeatherViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { AppRoot() }
    }
}

@Composable
fun AppRoot() {
    Weather360Theme {
        val navController = rememberNavController()
        val viewModel: WeatherViewModel = viewModel() // Create ViewModel
        WeatherScaffold(navController = navController, viewModel = viewModel)
    }
}