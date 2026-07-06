import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

dependencies {
    implementation(projects.shared)

    implementation(compose.desktop.currentOs)
    implementation(libs.kotlinx.coroutinesSwing)

    //koin
    implementation(libs.koin.core)
    implementation(libs.koin.compose)

    implementation(libs.compose.uiToolingPreview)
}

compose.desktop {
    application {
        mainClass = "com.yasinmoridi.miniverse.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "MiniVerse"
            packageVersion = "1.0.0"
            description = "MiniVerse Gaming Hub"
            copyright = "© 2024 Yasin Moridi"
            vendor = "Yasin Moridi"

            windows {
                menu = true
                // iconFile.set(project.file("icons/icon.ico")) 
            }
            macOS {
                bundleID = "com.yasinmoridi.miniverse"
            }
            linux {
                shortcut = true
            }
        }

        jvmArgs("-Xmx2G", "-XX:+UseG1GC")
    }
}