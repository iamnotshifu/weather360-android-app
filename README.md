# 🌦️ Weather360

Weather360 is a modern Android application built using **Jetpack Compose** to provide real-time weather information for any city worldwide. Powered by the **OpenWeatherMap API**, it displays temperature, weather description, and an icon representing current conditions. With a clean, intuitive UI, Weather360 offers a seamless experience for checking weather updates on the go.

## 📚 Table of Contents
- [✨ Features](#-features)
- [🖼️ Screenshots](#-screenshots)
- [⚙️ Installation](#️-installation)
- [🚀 Usage](#-usage)
- [🛠️ Technologies Used](#️-technologies-used)
- [🧩 Challenges Encountered](#-challenges-encountered)
- [👥 Team](#-team)
- [🤝 Contributing](#-contributing)
- [📄 License](#-license)

## ✨ Features
- 🌍 **City-based Weather Search**: Enter any city name to retrieve current weather data.
- ⏱️ **Real-time Weather Data**: Displays temperature (Celsius), weather description, and weather icon.
- 📱 **Responsive UI**: Built with Jetpack Compose for a modern, smooth, adaptive interface.
- ⏳ **Loading State Handling**: Shows a loading indicator during API fetches.
- ⚠️ **Error Handling**: Displays meaningful messages for invalid city names, network issues, or API errors.
- 🎭 **Animated UI**: Uses `AnimatedVisibility` for smooth transitions of weather information.
- 🌈 **Gradient Background**: Enhances the user experience with a visually appealing gradient.
- 📲 **Edge-to-Edge Display**: Supports full-screen UI on modern Android devices.
- ⚙️ **Settings & Credits**: Includes screens for API key status, unit selection, and team credits.

## 🖼️ Screenshots
*(Screenshots will be added after capturing from emulator/device.)*
- 🏠 **Home Screen**: City input field and "Get Weather" button.
- 🌡️ **Weather Display**: Temperature, description, and icon after a successful API call.
- ⚙️ **Settings Screen**: API key status and unit placeholder.
- 👥 **Credits Screen**: Team information.
- 🚫 **Error State**: Error message for invalid inputs or API failures.

## ⚙️ Installation
To set up and run Weather360 locally:

### 📋 Prerequisites
- 🖥️ **Android Studio**: Koala or later.
- ☕ **JDK**: Version 17 or higher.
- 🔑 **OpenWeatherMap API Key**: Obtain a free key from [OpenWeatherMap](https://openweathermap.org/).

### 🛠️ Steps
1. **Clone the Repository**:
    ```bash
    git clone https://github.com/iamnotshifu/weather360.git
    ```
2. **Open in Android Studio**:
   - File > Open > Select the cloned `weather360` folder.
3. **Add API Key**:
   - Create/edit `local.properties` in the project root:
     ```properties
     WEATHER_API_KEY=your_openweathermap_api_key
     ```
4. **Sync Project**:
   - Click **Sync Project with Gradle Files** in Android Studio.
5. **Run the App**:
   - Connect an Android device or start an emulator (API 24+).
   - Click **Run** to build and deploy.

## 🚀 Usage
1. Launch Weather360.
2. Enter a city name (e.g., "Lagos", "London").
3. Tap **Get Weather** to view 🌡️ temperature, 🌤️ description, and 🖼️ icon.
4. Navigate to **Settings** to check API key status or unit settings.
5. Visit **Credits** to see team details.
6. If an error occurs, 🚫 a friendly error message appears.

## 🛠️ Technologies Used
- 💻 **Language**: Kotlin
- 🎨 **UI Framework**: Jetpack Compose
- 🌐 **Networking**: Ktor Client (OkHttp)
- 🖼️ **Image Loading**: Coil for Compose
- 🔄 **Asynchronous Programming**: Kotlin Coroutines
- ☁️ **API**: OpenWeatherMap API
- 🛠️ **IDE**: Android Studio
- 📦 **Libraries**:
  - androidx.activity:activity-compose
  - androidx.compose.material3
  - io.coil-kt:coil-compose
  - io.ktor:ktor-client-core
  - io.ktor:ktor-client-okhttp
  - io.ktor:ktor-serialization-kotlinx-json
  - androidx.lifecycle:lifecycle-viewmodel-compose

## 🧩 Challenges Encountered
- 🌐 **API Integration**:
  - **Challenge**: Parsing JSON and handling API errors.
  - **Solution**: Used `JSONObject`, try-catch, and HTTP code checks in `OpenWeather.kt`.
- 🔄 **Asynchronous Operations**:
  - **Challenge**: Preventing UI freezes during network calls.
  - **Solution**: Used Coroutines with `Dispatchers.IO` and `rememberCoroutineScope`.
- 📱 **UI Responsiveness**:
  - **Challenge**: Smooth loading states.
  - **Solution**: Added `CircularProgressIndicator` and `AnimatedVisibility`.
- 🔑 **API Key Security**:
  - **Challenge**: Avoid exposing keys in public repos.
  - **Solution**: Stored in `local.properties` with `buildConfigField`.
- 📲 **Edge-to-Edge Display**:
  - **Challenge**: Ensuring UI compatibility across devices.
  - **Solution**: Used `enableEdgeToEdge()` and tested on API 24–35.
- 🚫 **Error Handling**:
  - **Challenge**: Preventing crashes on invalid inputs.
  - **Solution**: Input validation and user-friendly error messages.

## 👥 Team
- **Nyuiring-yoh Shifu-Nfor** – Lead Developer
  - 🛠️ App architecture, Jetpack Compose UI, API integration, signing
  - 🔗 GitHub: [nforshifu234](https://github.com/nforshifu234)
  - 📧 Email: nfor.shifu@example.com
- **Sunday Daramfon** – Developer
  - 🛠️ UI design, testing
  - 🔗 GitHub: [sunday-daramfon](https://github.com/sunday-daramfon)
- **Olawunmi Adebayo** – Developer
  - 🛠️ API integration, error handling
  - 🔗 GitHub: [olawunmi-adebayo](https://github.com/olawunmi-adebayo)
- **Michael Jasper** – Developer
  - 🛠️ Testing, documentation
  - 🔗 GitHub: [michael-jasper](https://github.com/michael-jasper)

## 🤝 Contributing
1. Fork the repo.
2. Create a branch:
    ```bash
    git checkout -b feature/your-feature
    ```
3. Commit changes:
    ```bash
    git commit -m "Add your feature"
    ```
4. Push:
    ```bash
    git push origin feature/your-feature
    ```
5. Open a PR.

## 📄 License
Licensed under the MIT License. See [LICENSE](LICENSE) for details.