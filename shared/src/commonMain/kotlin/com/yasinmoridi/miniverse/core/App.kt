package com.yasinmoridi.miniverse.core

import com.yasinmoridi.miniverse.presentation.core.theme.BibleTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.yasinmoridi.miniverse.presentation.core.navigation.SetUpNavGraph

@Composable
fun App() {
    BibleTheme {
        val navController = rememberNavController()
        SetUpNavGraph(navController)
    }
}

