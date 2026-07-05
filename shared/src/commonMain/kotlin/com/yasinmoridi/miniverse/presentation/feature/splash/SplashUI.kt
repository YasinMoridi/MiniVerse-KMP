package com.yasinmoridi.miniverse.presentation.feature.splash

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.yasinmoridi.miniverse.presentation.components.MultiPlayerHeader
import com.yasinmoridi.miniverse.presentation.core.navigation.AppDestination
import com.yasinmoridi.miniverse.utils.AppColor.BLUE_COLORFUL_BAR
import com.yasinmoridi.miniverse.utils.AppColor.BLUE_NAME_APP
import com.yasinmoridi.miniverse.utils.AppColor.GREEN_COLORFUL_BAR
import com.yasinmoridi.miniverse.utils.AppColor.GREEN_NAME_APP
import com.yasinmoridi.miniverse.utils.AppColor.ORANGE_COLORFUL_BAR
import com.yasinmoridi.miniverse.utils.AppColor.PINK_NAME_APP
import com.yasinmoridi.miniverse.utils.AppColor.RED_COLORFUL_BAR
import com.yasinmoridi.miniverse.utils.AppColor.RED_NAME_APP
import com.yasinmoridi.miniverse.utils.AppColor.GR_BG
import com.yasinmoridi.miniverse.utils.AppColor.SPLASH_GR_CIRCLE
import com.yasinmoridi.miniverse.utils.AppColor.YELLOW_COLORFUL_BAR
import com.yasinmoridi.miniverse.utils.AppColor.YELLOW_NAME_APP
import miniverse.shared.generated.resources.Res
import miniverse.shared.generated.resources.img_sword
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun SplashUI(
    navController: NavHostController,
    viewModel: SplashVM = koinViewModel()
) {
    var startAnimation by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 5000, easing = LinearEasing)
    )

    val isTimerFinished by viewModel.isTimerFinished.collectAsState()

    LaunchedEffect(Unit) {
        startAnimation = true
    }

    LaunchedEffect(progress, isTimerFinished) {
        if (progress == 1f && isTimerFinished) {
            navController.navigate(AppDestination.Type) {
                popUpTo(AppDestination.Splash) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GR_BG),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            MultiPlayerHeader(
                content = {
                    Spacer(modifier = Modifier.height(80.dp))

                    // Gradient circle
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape)
                            .background(SPLASH_GR_CIRCLE),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.img_sword),
                            contentDescription = "Splash Image",
                            modifier = Modifier.size(75.dp),
                        )
                    }
                }
            )
        }

        Box(
            modifier = Modifier
                .padding(bottom = 80.dp)
                .width(280.dp)
                .height(16.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFC4D1F5)),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF536EAF))
            )

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${(progress * 100).toInt()}%",
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@Composable
fun SlantedColorfulBar(
    modifier: Modifier = Modifier,
    barColors: List<Color> = listOf(
        RED_COLORFUL_BAR,
        BLUE_COLORFUL_BAR,
        GREEN_COLORFUL_BAR,
        YELLOW_COLORFUL_BAR,
        ORANGE_COLORFUL_BAR
    ),
    cornerRadius: Dp = 4.dp,
    skew: Dp = 6.dp
) {
    Canvas(
        modifier = modifier
            .width(300.dp)
            .height(12.dp)
            .clip(RoundedCornerShape(cornerRadius))
    ) {
        val n = barColors.size
        val segmentWidth = size.width / n
        val skewPx = skew.toPx()

        barColors.forEachIndexed { index, color ->
            val xStart = index * segmentWidth
            val xEnd = xStart + segmentWidth

            val path = Path().apply {
                moveTo(xStart + skewPx, 0f)
                lineTo(xEnd + skewPx, 0f)
                lineTo(xEnd - skewPx, size.height)
                lineTo(xStart - skewPx, size.height)
                close()
            }
            drawPath(path, color)
        }
    }
}
