package com.yasinmoridi.miniverse.presentation.feature.snake_bite

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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.yasinmoridi.miniverse.utils.AppColor
import com.yasinmoridi.miniverse.utils.UIStrings
import kotlinx.coroutines.delay
import miniverse.shared.generated.resources.Res
import miniverse.shared.generated.resources.img_apple
import miniverse.shared.generated.resources.img_bomb
import miniverse.shared.generated.resources.img_snake_head
import miniverse.shared.generated.resources.img_sword
import org.jetbrains.compose.resources.painterResource
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

enum class Direction {
    UP, DOWN, LEFT, RIGHT
}

data class Point(val x: Int, val y: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SnakeBiteUI(
    playerCount: Int,
    navController: NavHostController
) {
    val gridSize = 20
    var snake by remember { mutableStateOf(listOf(Point(10, 10), Point(10, 11), Point(10, 12))) }
    var food by remember { mutableStateOf(Point(5, 5)) }
    var bomb by remember { mutableStateOf(Point(15, 15)) }
    var direction by remember { mutableStateOf(Direction.UP) }
    var score by remember { mutableStateOf(0) }
    var isGameOver by remember { mutableStateOf(false) }

    // Game loop
    LaunchedEffect(isGameOver) {
        while (!isGameOver) {
            delay(200.milliseconds)
            val head = snake.first()
            val newHead = when (direction) {
                Direction.UP -> Point(head.x, head.y - 1)
                Direction.DOWN -> Point(head.x, head.y + 1)
                Direction.LEFT -> Point(head.x - 1, head.y)
                Direction.RIGHT -> Point(head.x + 1, head.y)
            }

            // Check collisions
            if (newHead.x !in 0..<gridSize || newHead.y < 0 || newHead.y >= gridSize || newHead in snake) {
                isGameOver = true
                break
            }

            if (newHead == bomb) {
                isGameOver = true
                break
            }

            if (newHead == food) {
                score++
                snake = listOf(newHead) + snake
                food = Point(Random.nextInt(gridSize), Random.nextInt(gridSize))
                bomb = Point(Random.nextInt(gridSize), Random.nextInt(gridSize))
            } else {
                snake = listOf(newHead) + snake.dropLast(1)
            }
        }
    }

    Scaffold(
        containerColor = Color(0xFFC8E6C9), // Light green background
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
                .padding(padding)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        val (x, y) = dragAmount
                        if (kotlin.math.abs(x) > kotlin.math.abs(y)) {
                            if (x > 0 && direction != Direction.LEFT) direction = Direction.RIGHT
                            else if (x < 0 && direction != Direction.RIGHT) direction = Direction.LEFT
                        } else {
                            if (y > 0 && direction != Direction.UP) direction = Direction.DOWN
                            else if (y < 0 && direction != Direction.DOWN) direction = Direction.UP
                        }
                    }
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(16.dp)
                    .background(Color(0xFF2E7D32).copy(alpha = 0.8f), RoundedCornerShape(8.dp))
                    .border(2.dp, Color(0xFF1B5E20), RoundedCornerShape(8.dp))
            ) {
                val tileSize = maxWidth / gridSize
                
                // Draw Grid
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val strokeWidth = 1.dp.toPx()
                    for (i in 0..gridSize) {
                        drawLine(
                            color = Color.White.copy(alpha = 0.1f),
                            start = Offset(i * tileSize.toPx(), 0f),
                            end = Offset(i * tileSize.toPx(), size.height),
                            strokeWidth = strokeWidth
                        )
                        drawLine(
                            color = Color.White.copy(alpha = 0.1f),
                            start = Offset(0f, i * tileSize.toPx()),
                            end = Offset(size.width, i * tileSize.toPx()),
                            strokeWidth = strokeWidth
                        )
                    }
                }

                // Food (Apple)
                GameElement(point = food, tileSize = tileSize) {
                    Image(
                        painter = painterResource(Res.drawable.img_apple),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                // Bomb
                GameElement(point = bomb, tileSize = tileSize) {
                    Image(
                        painter = painterResource(Res.drawable.img_bomb),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                // Snake
                snake.forEachIndexed { index, point ->
                    val isHead = index == 0
                    GameElement(
                        point = point,
                        tileSize = tileSize
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
                                    .padding(1.dp)
                                    .background(Color(0xFF81C784), RoundedCornerShape(4.dp))
                                    .border(1.dp, Color(0xFF4CAF50), RoundedCornerShape(4.dp))
                            )
                        }
                    }
                }
            }
            
            if (isGameOver) {
                Button(
                    onClick = {
                        snake = listOf(Point(10, 10), Point(10, 11), Point(10, 12))
                        direction = Direction.UP
                        isGameOver = false
                        score = 0
                    },
                    modifier = Modifier.padding(bottom = 32.dp)
                ) {
                    Text("GAME OVER - RESTART")
                }
            } else {
                Text(
                    text = "Score: $score",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
            }
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
