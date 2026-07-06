package com.yasinmoridi.miniverse.presentation.core.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.yasinmoridi.miniverse.presentation.feature.home.HomeUI
import com.yasinmoridi.miniverse.presentation.feature.game_info.GameInfoUI
import com.yasinmoridi.miniverse.presentation.feature.tic_tac_toe.TicTacToeUI
import com.yasinmoridi.miniverse.presentation.feature.settings.SettingsUI
import com.yasinmoridi.miniverse.presentation.feature.splash.SplashUI
import com.yasinmoridi.miniverse.presentation.feature.type.TypeUI

@Composable
fun SetUpNavGraph(navController: NavHostController) {
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
            val args = it.toRoute<AppDestination.Home>()
            HomeUI(playerCount = args.playerCount, navController = navController)
        }
        composable<AppDestination.Type> {
            TypeUI(navController = navController)
        }
        composable<AppDestination.Settings> {
            SettingsUI(navController = navController)
        }
        composable<AppDestination.GameInfo> {
            val args = it.toRoute<AppDestination.GameInfo>()
            GameInfoUI(gameName = args.gameName, navController = navController)
        }
        composable<AppDestination.TicTacToe> {
            TicTacToeUI(navController = navController)
        }
    }
}

