package com.yasinmoridi.miniverse.presentation.core.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yasinmoridi.miniverse.presentation.feature.home.HomeUI
import com.yasinmoridi.miniverse.presentation.feature.splash.SplashUI

@Composable
fun SetUpNavGraph(navController: NavHostController) {
    fun navigateBottom(index: Int) {
        val destination = when (index) {
            0 -> AppDestination.Home
            1 -> AppDestination.Bible
            2 -> AppDestination.FindPlan
            3 -> AppDestination.FaithJourney
            else -> AppDestination.Home
        }
        navController.navigate(destination) {
            popUpTo(AppDestination.Home) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    NavHost(
        navController = navController,
        startDestination = AppDestination.Splash,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable<AppDestination.Splash> {
            SplashUI(navController = navController)
        }
        composable<AppDestination.Home> {
            HomeUI()
        }
    }
}

