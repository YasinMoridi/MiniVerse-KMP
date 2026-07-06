package com.yasinmoridi.miniverse.presentation.feature.snake_bite

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.yasinmoridi.miniverse.presentation.components.GameOverPopup
import com.yasinmoridi.miniverse.utils.AppColor
import com.yasinmoridi.miniverse.utils.UIStrings
import kotlinx.coroutines.delay
import miniverse.shared.generated.resources.Res
import miniverse.shared.generated.resources.img_apple
import miniverse.shared.generated.resources.img_bomb
import miniverse.shared.generated.resources.img_snake_head
import miniverse.shared.generated.resources.img_sword
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SnakeBiteUI(
    playerCount: Int,
    navController: NavHostController,
    viewModel: SnakeBiteVM = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val gridSize = state.gridSize

    // Animation for food pulsing
    val infiniteTransition = rememberInfiniteTransition()
    val foodScale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Scaffold(
        containerColor = Color(0xFFF1F8E9), // Softer light green
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = UIStrings.SNAKE_BITE,
                        fontWeight = FontWeight.Black,
                        fontSize = 24.sp,
                        color = AppColor.DARK_NAVY
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = UIStrings.BACK,
                            tint = AppColor.DARK_NAVY,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = null,
                            tint = AppColor.DARK_NAVY,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Score Display with scale animation
            val scoreScale by animateFloatAsState(
                targetValue = 1f + (state.score % 10 * 0.05f),
                animationSpec = spring(stiffness = Spring.StiffnessLow)
            )

            Text(
                text = "Score: ${state.score}",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF2E7D32),
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .scale(scoreScale)
            )

            BoxWithConstraints(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(16.dp)
                    .shadow(8.dp, RoundedCornerShape(12.dp))
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFF388E3C), Color(0xFF2E7D32))
                        ),
                        RoundedCornerShape(12.dp)
                    )
                    .border(3.dp, Color(0xFF1B5E20), RoundedCornerShape(12.dp))
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            val (x, y) = dragAmount
                            if (kotlin.math.abs(x) > kotlin.math.abs(y)) {
                                if (x > 0) viewModel.changeDirection(Direction.RIGHT)
                                else if (x < 0) viewModel.changeDirection(Direction.LEFT)
                            } else {
                                if (y > 0) viewModel.changeDirection(Direction.DOWN)
                                else if (y < 0) viewModel.changeDirection(Direction.UP)
                            }
                        }
                    }
            ) {
                val tileSize = maxWidth / gridSize
                
                // Draw Grid (Subtle)
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val strokeWidth = 0.5.dp.toPx()
                    for (i in 0..gridSize) {
                        drawLine(
                            color = Color.White.copy(alpha = 0.05f),
                            start = Offset(i * tileSize.toPx(), 0f),
                            end = Offset(i * tileSize.toPx(), size.height),
                            strokeWidth = strokeWidth
                        )
                        drawLine(
                            color = Color.White.copy(alpha = 0.05f),
                            start = Offset(0f, i * tileSize.toPx()),
                            end = Offset(size.width, i * tileSize.toPx()),
                            strokeWidth = strokeWidth
                        )
                    }
                }

                // Food (Pulsing)
                GameElement(point = state.food, tileSize = tileSize) {
                    Image(
                        painter = painterResource(Res.drawable.img_apple),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .scale(foodScale)
                    )
                }

                // Bomb
                GameElement(point = state.bomb, tileSize = tileSize) {
                    Image(
                        painter = painterResource(Res.drawable.img_bomb),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                // Snake with smooth movement animation
                state.snake.forEachIndexed { index, point ->
                    val isHead = index == 0
                    
                    val animatedX by animateFloatAsState(
                        targetValue = point.x.toFloat(),
                        animationSpec = tween(durationMillis = state.gameSpeed.toInt(), easing = LinearEasing)
                    )
                    val animatedY by animateFloatAsState(
                        targetValue = point.y.toFloat(),
                        animationSpec = tween(durationMillis = state.gameSpeed.toInt(), easing = LinearEasing)
                    )

                    Box(
                        modifier = Modifier
                            .offset { 
                                IntOffset(
                                    (animatedX * tileSize.toPx()).toInt(), 
                                    (animatedY * tileSize.toPx()).toInt()
                                ) 
                            }
                            .size(tileSize)
                    ) {
                        if (isHead) {
                            Image(
                                painter = painterResource(Res.drawable.img_snake_head),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(2.dp)
                                    .background(
                                        Color(0xFF81C784),
                                        RoundedCornerShape((tileSize / 4).value.dp)
                                    )
                                    .border(1.dp, Color(0xFF4CAF50), RoundedCornerShape((tileSize / 4).value.dp))
                            )
                        }
                    }
                }

                // Game Over Overlay
                GameOverPopup(
                    visible = state.isGameOver,
                    text = "GAME OVER 🐍",
                    color = Color(0xFF1B5E20),
                    onClick = { viewModel.startGame() }
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun GameElement(
    point: Point,
    tileSize: androidx.compose.ui.unit.Dp,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .offset { IntOffset((point.x * tileSize.toPx()).toInt(), (point.y * tileSize.toPx()).toInt()) }
            .size(tileSize)
    ) {
        content()
    }
}
