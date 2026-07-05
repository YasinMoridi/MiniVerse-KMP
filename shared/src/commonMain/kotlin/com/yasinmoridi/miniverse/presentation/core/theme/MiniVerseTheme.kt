package com.yasinmoridi.miniverse.presentation.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class MiniVerseColors(
    val primaryText: Color,
    val secondaryText: Color,
    val appBackground: Color,
    val border: Color,
    val cardBackground: Color
)

val LocalMiniVerseColors = staticCompositionLocalOf {
    MiniVerseColors(
        primaryText = Color.Unspecified,
        secondaryText = Color.Unspecified,
        appBackground = Color.Unspecified,
        border = Color.Unspecified,
        cardBackground = Color.Unspecified
    )
}

object MiniVerseTheme {
    val colors: MiniVerseColors
        @Composable
        @ReadOnlyComposable
        get() = LocalMiniVerseColors.current
}

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6750A4),
    onPrimary = Color.White,
    secondary = Color(0xFF625B71),
    onSecondary = Color.White,
    tertiary = Color(0xFF7D5260),
    onTertiary = Color.White,
    background = Color(0xFFFFFBFE),
    onBackground = Color(0xFF1C1B1F),
    surface = Color(0xFFFFFBFE),
    onSurface = Color(0xFF1C1B1F)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFD0BCFF),
    onPrimary = Color(0xFF381E72),
    secondary = Color(0xFFCCC2DC),
    onSecondary = Color(0xFF332D41),
    tertiary = Color(0xFFEFB8C8),
    onTertiary = Color(0xFF492532),
    background = Color(0xFF1C1B1F),
    onBackground = Color(0xFFE6E1E5),
    surface = Color(0xFF1C1B1F),
    onSurface = Color(0xFFE6E1E5)
)

@Composable
fun MiniVerseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    
    val miniVerseColors = if (darkTheme) {
        MiniVerseColors(
            primaryText = Color(0xFFE6E1E5),
            secondaryText = Color(0xFF938F99),
            appBackground = Color(0xFF1C1B1F),
            border = Color(0xFF49454F),
            cardBackground = Color(0xFF2B2930)
        )
    } else {
        MiniVerseColors(
            primaryText = Color(0xFF1C1B1F),
            secondaryText = Color(0xFF49454F),
            appBackground = Color(0xFFFFFBFE),
            border = Color(0xFFCAC4D0),
            cardBackground = Color.White
        )
    }

    CompositionLocalProvider(LocalMiniVerseColors provides miniVerseColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }
}
