package com.example.weather360.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class WeatherViewModel : ViewModel() {
    var currentCity by mutableStateOf("Lagos, Nigeria") // Default city
}