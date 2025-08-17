import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.weather360"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.weather360"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "2.0"
        ndkVersion = "29.0.13846066"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Load API key from local.properties
        val localProps = Properties().apply {
            load(FileInputStream(rootProject.file("local.properties")))
        }
        val apiKey = localProps.getProperty("WEATHER_API_KEY") ?: ""
        buildConfigField("String", "WEATHER_API_KEY", "\"$apiKey\"")
    }

    signingConfigs {
        create("release") {
            storeFile = file("C:/Users/nfors/Documents/NFORSHIFU234_DEV/Weather360Apk/weather360-keystore-final.jks")
            storePassword = "K3y\$tor3!W360#2025"
            keyAlias = "weather360-key"
            keyPassword = "W3ath3r!K3y#G11\$"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
        debug {
            isDebuggable = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.material3)
    implementation("androidx.compose.material:material-icons-extended:1.7.1")
    implementation(libs.navigation.compose)
    implementation(libs.coroutines.android)
    implementation(libs.coil.compose)
    implementation(libs.core.splashscreen)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}