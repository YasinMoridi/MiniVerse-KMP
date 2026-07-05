# 🌌 MiniVerse

## 📜 Description  
**MiniVerse** is a modern, cross-platform gaming hub designed to provide a seamless and engaging experience for solo and multiplayer mini-games.  
Built with **Kotlin Multiplatform (KMP)** and **Compose Multiplatform**, the app runs natively on **Android, iOS, Desktop (Windows/macOS/Linux), and Web**.  
It follows modern Android development practices, utilizing **Material 3** for a vibrant UI and **Koin** for dependency injection, ensuring a scalable and maintainable codebase.

## ✨ Features
- 🌍 **Cross-Platform:** Single codebase for Android, iOS, Desktop, and Web.
- 🎮 **Multiplayer Focus:** Support for 1 to 4 players with a dedicated player selection interface.
- 🕹️ **Mini-Game Hub:** A central place to discover and play various mini-games.
- 🎨 **Material 3 UI:** Modern, responsive design with customized player-themed aesthetics.
- ⚙️ **Customizable Settings:** Easy navigation to app settings and configurations.
- 📲 **Social Sharing:** Built-in sharing functionality to invite friends to the MiniVerse.
- 🚀 **Smooth UX:** Optimized scrolling and drag interactions for a premium feel.

## 🛠 Built With

| Category                  | Technology                                                                                                  |
|---------------------------|-------------------------------------------------------------------------------------------------------------|
| 🏛 Architecture            | Kotlin Multiplatform (KMP)                                                                                  |
| 🖼️ UI Framework            | [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform) (Material 3)                   |
| 🛠️ Dependency Injection    | [Koin](https://insert-koin.io/)                                                                             |
| 🌐 Networking              | [Ktor](https://ktor.io/)                                                                                    |
| 📜 Serialization           | [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization)                                   |
| 🧭 Navigation              | [Compose Navigation (JetBrains)](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-navigation.html) |
| 🔄 Coroutines              | [Kotlinx Coroutines](https://github.com/Kotlin/kotlinx.coroutines)                                         |

## 🚀 Running the App

### Android
```bash
./gradlew :androidApp:assembleDebug
```

### Desktop (JVM)
- **Standard Run:** `./gradlew :desktopApp:run`

### Web
- **Wasm Target:** `./gradlew :webApp:wasmJsBrowserDevelopmentRun`
- **JS Target:** `./gradlew :webApp:jsBrowserDevelopmentRun`

### iOS
Open the `iosApp` directory in Xcode and run the project.

<!--
## 📱 Screenshots

<table style="width:100%">
  <tr>
    <th>Player Selection</th>
    <th>Game Mode</th> 
    <th>Settings</th> 
  </tr>
  <tr>
    <td><img src="screenshot/player_selection.png" width=240/></td> 
    <td><img src="screenshot/game_mode.png" width=240/></td>
    <td><img src="screenshot/settings.png" width=240/></td>
  </tr>
</table>
-->
