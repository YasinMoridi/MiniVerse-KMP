import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalDistributionDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    js {
        @OptIn(ExperimentalDistributionDsl::class)
        browser {
            commonWebpackConfig {
                outputFileName = "miniverse.js"
            }
        }
        binaries.executable()
    }

    @OptIn(ExperimentalWasmDsl::class, ExperimentalDistributionDsl::class)
    wasmJs {
        browser {
            commonWebpackConfig {
                outputFileName = "miniverse-wasm.js"
            }
        }
        binaries.executable()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared)

            //koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose)

            implementation(libs.compose.ui)
        }
    }
}