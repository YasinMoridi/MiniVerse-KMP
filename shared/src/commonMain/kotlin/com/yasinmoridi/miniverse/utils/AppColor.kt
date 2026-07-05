package com.yasinmoridi.miniverse.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object AppColor {

    // Player Colors (Buttons & Badges)
    val RED_PLAYER_BTN = Color(0xFFFF5252)
    val BLUE_PLAYER_BTN = Color(0xFF448AFF)
    val GREEN_PLAYER_BTN = Color(0xFF66BB6A)
    val YELLOW_PLAYER_BTN = Color(0xFFFFB74D)

    val RED_PLAYER_BDG = Color(0xFFB71C1C)
    val BLUE_PLAYER_BDG = Color(0xFF0277BD)
    val GREEN_PLAYER_BDG = Color(0xFF2E7D32)
    val YELLOW_PLAYER_BDG = Color(0xFFEF6C00)

    // Name Colors
    val RED_NAME_APP = Color(0xFFD30000)
    val BLUE_NAME_APP = Color(0xFF0004FF)
    val GREEN_NAME_APP = Color(0xFF1BB632)
    val YELLOW_NAME_APP = Color(0xFFFF7802)
    val PINK_NAME_APP = Color(0xFFFF0EE7)

    // Colorful Bar
    val RED_COLORFUL_BAR = Color(0xFFFF6B6B)
    val BLUE_COLORFUL_BAR = Color(0xFF4A90E2)
    val GREEN_COLORFUL_BAR = Color(0xFF7ED321)
    val YELLOW_COLORFUL_BAR = Color(0xFFFF7802)
    val ORANGE_COLORFUL_BAR = Color(0xFFF5C542)

    // Common UI Colors
    val DARK_NAVY = Color(0xFF2E3B62)
    val SHARE_BTN_GREEN = Color(0xFF1BB632)
    val LINE_DECORATION = Color.White.copy(alpha = 0.2f)
    val PROGRESS_BG = Color(0xFFC4D1F5)
    val PROGRESS_FILL = Color(0xFF536EAF)

    // Gradients
    val GR_BG = Brush.verticalGradient(
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
}
