package com.yasinmoridi.miniverse.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object AppColor {


    //Names
    val RED_NAME_APP = Color(0xFFD30000)
    val BLUE_NAME_APP = Color(0xFF0004FF)
    val GREEN_NAME_APP = Color(0xFF1BB632)
    val YELLOW_NAME_APP = Color(0xFFFF7802)
    val PINK_NAME_APP = Color(0xFFFF0EE7)

    //Colorful bar

    val RED_COLORFUL_BAR = Color(0xFFFF6B6B)
    val BLUE_COLORFUL_BAR = Color(0xFF4A90E2)
    val GREEN_COLORFUL_BAR = Color(0xFF7ED321)
    val YELLOW_COLORFUL_BAR = Color(0xFFFF7802)
    val ORANGE_COLORFUL_BAR = Color(0xFFF5C542)

    //GR
    val SPLASH_GR_BG = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFE0D7FF),
            Color(0xFFF3EEFF)
        )
    )
    val SPLASH_GR_CIRCLE = Brush.linearGradient(
        colors = listOf(
            Color(0xFFFF9A9E),
            Color(0xFFFAD0C4),
            Color(0xFFA1C4FD),
        ),
        start = Offset(0f, 0f),
        end = Offset(0f, Float.POSITIVE_INFINITY)
    )

    // BACKGROUND
    val DARK_APP_BG = Color(0xFF0A182C)
    val LIGHT_APP_BG = Color(0xFFE2EDF7)

    // CARD BACKGROUNDS (Light Mode)
    val YELLOW_LIGHT_BG = Color(0xFFFFFBF0)
    val PURPLE_LIGHT_BG = Color(0xFFF5F3FF)

    // CARD BACKGROUNDS (Dark Mode)
    val YELLOW_DARK_BG = Color(0xFF1F1A0E)
    val PURPLE_DARK_BG = Color(0xFF170F2A)

    // ACCENT COLORS
    val YELLOW_600 = Color(0xFFF59E0B)
    val PURPLE_600 = Color(0xFF7C3AED)

    // TEXT COLORS
    val DEEP_NAVY = Color(0xFF1A2744) // For Light Mode
    val OFF_WHITE = Color(0xFFE2E8F0) // For Dark Mode
    val SOFT_BLUE_GRAY = Color(0xFF6B8AAA)
    val DARK_BLUE_GRAY = Color(0xFF4A5568)

    // SEMANTIC COLORS
    val SUCCESS = Color(0xFF22C55E)
    val ERROR = Color(0xFFEF4444)
    val LIGHT_BORDER = Color(0xFFE2E8F0)
    val DARK_BORDER = Color(0xFF1E293B)
    val QUIZ_CARD_LIGHT = Color.White
    val QUIZ_CARD_DARK = Color(0xFF1E293B)

    // ADDITIONAL COLORS
    val BLUE_500 = Color(0xFF3B82F6)
    val PINK_500 = Color(0xFFEC4899)
    val ORANGE_500 = Color(0xFFF97316)
    
    val LIGHT_CHALLENGE_BG = Color(0xFFFFFBF0)
    val DARK_CHALLENGE_BG = Color(0xFF2D2415)
    
    val LIGHT_MORNING_BG = Color(0xFFFFF7ED)
    val DARK_MORNING_BG = Color(0xFF2A1F1A)
    val MORNING_ICON = Color(0xFFF59E0B)

}


