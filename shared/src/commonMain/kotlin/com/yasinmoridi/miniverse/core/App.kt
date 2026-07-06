package com.yasinmoridi.miniverse.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.compose.rememberNavController
import com.yasinmoridi.miniverse.presentation.core.navigation.SetUpNavGraph
import com.yasinmoridi.miniverse.presentation.core.theme.MiniVerseTheme
import com.yasinmoridi.miniverse.utils.AppLanguage
import com.yasinmoridi.miniverse.utils.UIStrings

@Composable
fun App() {
    val layoutDirection = if (UIStrings.language == AppLanguage.PERSIAN) {
        LayoutDirection.Rtl
    } else {
        LayoutDirection.Ltr
    }

    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
        MiniVerseTheme {
            val navController = rememberNavController()
            SetUpNavGraph(navController)
        }
    }
}
