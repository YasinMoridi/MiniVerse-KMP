package com.yasinmoridi.miniverse.presentation.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.yasinmoridi.miniverse.utils.AppColor

data class MiniVerseColors(
    val morningCardBg: Color,
    val eveningCardBg: Color,
    val primaryText: Color,
    val secondaryText: Color,
    val appBackground: Color,
    val quizCardBg: Color,
    val border: Color,
    val challengeBg: Color,
    val card2: Color,
    val morningBg: Color,
    val morningIcon: Color
)

val LocalMiniVerseColors = staticCompositionLocalOf {
    MiniVerseColors(
        morningCardBg = Color.Unspecified,
        eveningCardBg = Color.Unspecified,
        primaryText = Color.Unspecified,
        secondaryText = Color.Unspecified,
        appBackground = Color.Unspecified,
        quizCardBg = Color.Unspecified,
        border = Color.Unspecified,
        challengeBg = Color.Unspecified,
        card2 = Color.Unspecified,
        morningBg = Color.Unspecified,
        morningIcon = Color.Unspecified
    )
}



object MiniVerseTheme {
    val colors: MiniVerseColors
        @Composable
        @ReadOnlyComposable
        get() = LocalMiniVerseColors.current
}

private val LightColorScheme = lightColorScheme(
    primary = AppColor.YELLOW_600,
    background = AppColor.LIGHT_APP_BG,
    surface = AppColor.LIGHT_APP_BG,
    onPrimary = Color.White,
    onBackground = AppColor.DEEP_NAVY,
    onSurface = AppColor.DEEP_NAVY
)

private val DarkColorScheme = darkColorScheme(
    primary = AppColor.YELLOW_600,
    background = AppColor.DARK_APP_BG,
    surface = AppColor.DARK_APP_BG,
    onPrimary = Color.White,
    onBackground = AppColor.OFF_WHITE,
    onSurface = AppColor.OFF_WHITE
)

@Composable
fun MiniVerseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    
    val miniVerseColors = if (darkTheme) {
        MiniVerseColors(
            morningCardBg = AppColor.YELLOW_DARK_BG,
            eveningCardBg = AppColor.PURPLE_DARK_BG,
            primaryText = AppColor.OFF_WHITE,
            secondaryText = AppColor.SOFT_BLUE_GRAY,
            appBackground = AppColor.DARK_APP_BG,
            quizCardBg = AppColor.QUIZ_CARD_DARK,
            border = AppColor.DARK_BORDER,
            challengeBg = AppColor.DARK_CHALLENGE_BG,
            card2 = AppColor.PURPLE_DARK_BG,
            morningBg = AppColor.DARK_MORNING_BG,
            morningIcon = AppColor.MORNING_ICON
        )
    } else {
        MiniVerseColors(
            morningCardBg = AppColor.YELLOW_LIGHT_BG,
            eveningCardBg = AppColor.PURPLE_LIGHT_BG,
            primaryText = AppColor.DEEP_NAVY,
            secondaryText = AppColor.DARK_BLUE_GRAY,
            appBackground = AppColor.LIGHT_APP_BG,
            quizCardBg = AppColor.QUIZ_CARD_LIGHT,
            border = AppColor.LIGHT_BORDER,
            challengeBg = AppColor.LIGHT_CHALLENGE_BG,
            card2 = AppColor.PURPLE_LIGHT_BG,
            morningBg = AppColor.LIGHT_MORNING_BG,
            morningIcon = AppColor.MORNING_ICON
        )
    }



    CompositionLocalProvider(LocalMiniVerseColors provides miniVerseColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }
}
