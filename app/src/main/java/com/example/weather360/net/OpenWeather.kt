package com.example.weather360.net

import com.example.weather360.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.net.URL

object OpenWeather {
    private const val BASE = "https://api.openweathermap.org"
    private val apiKey: String get() = BuildConfig.WEATHER_API_KEY

    data class GeoResult(val name: String, val lat: Double, val lon: Double, val country: String?)

    suspend fun geocodeCity(city: String): Result<GeoResult> = withContext(Dispatchers.IO) {
        if (apiKey.isBlank()) return@withContext Result.failure(IllegalStateException("API key missing"))
        val url = "$BASE/geo/1.0/direct?q=${city.trim()}&limit=1&appid=$apiKey"
        httpGet(url).mapCatching { body ->
            val arr = JSONObject("{\"root\":$body}").getJSONArray("root")
            if (arr.length() == 0) error("City not found")
            val obj = arr.getJSONObject(0)
            GeoResult(
                name = obj.optString("name", city),
                lat = obj.getDouble("lat"),
                lon = obj.getDouble("lon"),
                country = obj.getString("country")?.takeIf { it.isNotEmpty() } // Fix: Safe string access
            )
        }
    }

    suspend fun oneCall(lat: Double, lon: Double): Result<JSONObject> = withContext(Dispatchers.IO) {
        if (apiKey.isBlank()) return@withContext Result.failure(IllegalStateException("API key missing"))
        val url = "$BASE/data/3.0/onecall?lat=$lat&lon=$lon&appid=$apiKey&units=metric"
        httpGet(url).mapCatching { JSONObject(it) }
    }

    suspend fun currentByCity(city: String): Result<JSONObject> = withContext(Dispatchers.IO) {
        if (apiKey.isBlank()) return@withContext Result.failure(IllegalStateException("API key missing"))
        val url = "$BASE/data/2.5/weather?q=${city.trim()}&appid=$apiKey&units=metric"
        httpGet(url).mapCatching { JSONObject(it) }
    }

    suspend fun forecastByCity(city: String): Result<JSONObject> = withContext(Dispatchers.IO) {
        if (apiKey.isBlank()) return@withContext Result.failure(IllegalStateException("API key missing"))
        val url = "$BASE/data/2.5/forecast?q=${city.trim()}&appid=$apiKey&units=metric"
        httpGet(url).mapCatching { JSONObject(it) }
    }

    private fun httpGet(urlStr: String): Result<String> {
        return try {
            val url = URL(urlStr)
            val conn = (url.openConnection() as HttpURLConnection).apply {
                connectTimeout = 8000
                readTimeout = 8000
                requestMethod = "GET"
            }
            val code = conn.responseCode
            val stream = if (code in 200..299) conn.inputStream else conn.errorStream
            val body = stream.bufferedReader().use(BufferedReader::readText)
            if (code in 200..299) Result.success(body)
            else {
                val msg = try {
                    JSONObject(body).optString("message", "Unknown error")
                } catch (e: Exception) {
                    body.take(200)
                }
                Result.failure(IllegalStateException("HTTP $code $msg"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}