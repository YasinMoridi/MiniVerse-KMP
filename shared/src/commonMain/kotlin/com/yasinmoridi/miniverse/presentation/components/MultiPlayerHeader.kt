package com.yasinmoridi.miniverse.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yasinmoridi.miniverse.presentation.feature.splash.SlantedColorfulBar
import com.yasinmoridi.miniverse.utils.AppColor.BLUE_NAME_APP
import com.yasinmoridi.miniverse.utils.AppColor.GREEN_NAME_APP
import com.yasinmoridi.miniverse.utils.AppColor.PINK_NAME_APP
import com.yasinmoridi.miniverse.utils.AppColor.RED_NAME_APP
import com.yasinmoridi.miniverse.utils.AppColor.YELLOW_NAME_APP
import com.yasinmoridi.miniverse.utils.UIStrings
import miniverse.shared.generated.resources.Res
import miniverse.shared.generated.resources.img_sword
import org.jetbrains.compose.resources.painterResource

@Composable
fun MultiPlayerHeader(
    content: @Composable () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        // MULTI text
        val multiText = buildAnnotatedString {
            val colors = listOf(
                RED_NAME_APP,
                BLUE_NAME_APP,
                GREEN_NAME_APP,
                YELLOW_NAME_APP,
                PINK_NAME_APP
            )
            UIStrings.MULTI.forEachIndexed { index, char ->
                withStyle(style = SpanStyle(color = colors[index % colors.size])) {
                    append(char)
                }
            }
        }
        Text(
            text = multiText,
            fontSize = 32.sp,
            fontWeight = FontWeight.Black
        )

        Text(
            text = UIStrings.PLAYER_GAMES,
            fontSize = 36.sp,
            fontWeight = FontWeight.Black,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Colorful bar
        SlantedColorfulBar()

        Spacer(modifier = Modifier.height(20.dp))

        content()
    }
}