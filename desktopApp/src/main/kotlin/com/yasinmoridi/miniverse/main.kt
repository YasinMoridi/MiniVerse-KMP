package com.yasinmoridi.miniverse

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.yasinmoridi.miniverse.core.App
import com.yasinmoridi.miniverse.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "MiniVerse",
        ) {
            App()
        }
    }
}