package com.yasinmoridi.miniverse.presentation.core.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.yasinmoridi.miniverse.presentation.core.theme.MiniVerseTheme
import com.yasinmoridi.miniverse.utils.AppColor

@Composable
fun BottomNavBar(
    activeIndex: Int,
    onNavigate: (Int) -> Unit
) {
    NavigationBar(
        containerColor = MiniVerseTheme.colors.quizCardBg,
        contentColor = MiniVerseTheme.colors.primaryText
    ) {
        val items = listOf(
            Triple(Icons.Default.Home, "Home", 0),
            Triple(Icons.Default.Book, "Bible", 1),
            Triple(Icons.Default.Search, "Plan", 2),
            Triple(Icons.Default.Person, "Journey", 3)
        )

        items.forEach { (icon, label, index) ->
            NavigationBarItem(
                selected = activeIndex == index,
                onClick = { onNavigate(index) },
                icon = { Icon(icon, contentDescription = label) },
                label = { Text(label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AppColor.YELLOW_600,
                    selectedTextColor = AppColor.YELLOW_600,
                    unselectedIconColor = MiniVerseTheme.colors.secondaryText,
                    unselectedTextColor = MiniVerseTheme.colors.secondaryText,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}
