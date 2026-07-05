package com.yasinmoridi.miniverse.presentation.core.navigation

import kotlinx.serialization.Serializable

sealed interface AppDestination {

    @Serializable
    data object Splash : AppDestination

    @Serializable
    data object Home : AppDestination

    @Serializable
    data object Type : AppDestination
}


