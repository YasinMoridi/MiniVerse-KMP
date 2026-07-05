package com.yasinmoridi.miniverse.presentation.feature.splash

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun SplashUI(
    navController: NavHostController,
    viewModel: SplashVM = koinViewModel()
) {

}
