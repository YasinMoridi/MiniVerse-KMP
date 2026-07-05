package com.yasinmoridi.miniverse.presentation.core.navigation

import kotlinx.serialization.Serializable

sealed interface AppDestination {

    @Serializable
    data object Splash : AppDestination

    @Serializable
    data object Home : AppDestination

    @Serializable
    data object Quiz : AppDestination

    @Serializable
    data object Bible : AppDestination

    @Serializable
    data object FindPlan : AppDestination

    @Serializable
    data object VersePrayer : AppDestination

    @Serializable
    data object PlanDetail : AppDestination

    @Serializable
    data object Mood : AppDestination

    @Serializable
    data object FaithJourney : AppDestination
}


