package com.yasinmoridi.miniverse.presentation.components

import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameOverPopup(
    visible: Boolean,
    text: String,
    color: Color = Color.Gray,
    onClick: () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = scaleIn(spring(dampingRatio = Spring.DampingRatioHighBouncy)) + fadeIn(),
        exit = scaleOut() + fadeOut()
    ) {
        Surface(
            modifier = Modifier
                .clickable { onClick() }
                .shadow(8.dp, RoundedCornerShape(20.dp))
                .border(2.dp, Color.White, RoundedCornerShape(20.dp)),
            color = color,
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 12.dp),
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}
