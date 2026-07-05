package com.yasinmoridi.miniverse.presentation.core.navigation

import kotlinx.serialization.Serializable

sealed interface AppDestination {

    @Serializable
    data object Splash : AppDestination

    @Serializable
    data class Home(val playerCount: Int) : AppDestination

    @Serializable
    data object Type : AppDestination

    @Serializable
    data object Settings : AppDestination

    @Serializable
    data class GameInfo(val gameName: String) : AppDestination
}


