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

## 🚀 Development & Deployment

### 🛠 Development Run
| Platform | Command |
| :--- | :--- |
| **Android** | `./gradlew :androidApp:installDebug` |
| **Desktop** | `./gradlew :desktopApp:run` |
| **Web (Wasm)** | `./gradlew :webApp:wasmJsBrowserDevelopmentRun` |
| **Web (JS)** | `./gradlew :webApp:jsBrowserDevelopmentRun` |
| **iOS** | Open `iosApp` in Xcode and Run |

### 📦 Production Build (Release)
| Platform | Build Command | Output Format |
| :--- | :--- | :--- |
| **Android** | `./gradlew :androidApp:assembleRelease` | `.apk` (Signed) |
| **Windows** | `./gradlew :desktopApp:packageMsi` | `.msi` |
| **macOS** | `./gradlew :desktopApp:packageDmg` | `.dmg` |
| **Linux** | `./gradlew :desktopApp:packageDeb` | `.deb` |
| **Web (Wasm)** | `./gradlew :webApp:wasmJsBrowserDistribution` | Static Files |
| **Web (JS)** | `./gradlew :webApp:jsBrowserDistribution` | Static Files |
| **iOS** | `./gradlew :shared:linkReleaseFrameworkIosArm64` | `.framework` |

### 📂 Build Artifacts Location
| Platform | Output Path |
| :--- | :--- |
| **Android** | `androidApp/build/outputs/apk/release/` |
| **Windows** | `desktopApp/build/compose/binaries/main/msi/` |
| **macOS** | `desktopApp/build/compose/binaries/main/dmg/` |
| **Linux** | `desktopApp/build/compose/binaries/main/deb/` |
| **Web (Wasm)** | `webApp/build/dist/wasmJs/productionExecutable/` |
| **Web (JS)** | `webApp/build/dist/js/productionExecutable/` |
| **iOS** | `shared/build/bin/iosArm64/releaseFramework/` |

### 🤖 Automated CI/CD (GitHub Actions)
This project is equipped with GitHub Actions for automated building of all platforms, including macOS and iOS, which would otherwise require a Mac computer.

#### 🛠️ Setup GitHub Secrets
To enable the automated Android build (Signed APK), you must add the following secrets to your GitHub repository:
1. Go to **Settings** > **Secrets and variables** > **Actions**.
2. Click **New repository secret** and add:
   - `RELEASE_STORE_PASSWORD`: Your Keystore password.
   - `RELEASE_KEY_ALIAS`: Your Key alias (e.g., `miniverse-alias`).
   - `RELEASE_KEY_PASSWORD`: Your Key password.

#### 📥 How to get the builds
1. Every time you **push** your code to the `main` or `master` branch, a build will start automatically.
2. Go to the **Actions** tab in your GitHub repository.
3. Select the latest workflow run.
4. Once completed, scroll down to the **Artifacts** section to download the builds for Android, Windows, macOS, Linux, and Web.

## 📱 Screenshots

<!--
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
