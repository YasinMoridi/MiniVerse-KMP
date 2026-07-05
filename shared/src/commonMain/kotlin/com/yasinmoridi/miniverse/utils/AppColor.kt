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
    
    // Home Screen (From Image)
    val HOME_BG = Color(0xFFE3F2FD)
    val CARD_BORDER = Color(0xFF56B7FF)
    val CARD_TEXT_BOX_BLUE = Color(0xFF0B5CC6)
    val CARD_TEXT_BOX_PURPLE = Color(0xFF8738ED)
    val CARD_TEXT_BOX_GREEN = Color(0xFF547024)
    val CARD_TEXT_BOX_RED = Color(0xFFEA2F03)
    val CARD_TEXT_BOX_PINK = Color(0xFFDD4281)
    val CARD_TEXT_BOX_ORANGE = Color(0xFFFF9718)
    val CARD_TEXT_BOX_GRAY = Color(0xFFA8816F)
    val CARD_TEXT_BOX_YELLOW = Color(0xFFFFC926)
    val CARD_TEXT_BOX_CYAN = Color(0xFF38CEFF)

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
