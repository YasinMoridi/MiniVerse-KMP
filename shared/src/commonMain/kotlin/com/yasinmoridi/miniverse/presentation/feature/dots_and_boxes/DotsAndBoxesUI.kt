package com.yasinmoridi.miniverse.presentation.feature.dots_and_boxes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.yasinmoridi.miniverse.utils.AppColor
import com.yasinmoridi.miniverse.utils.UIStrings
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DotsAndBoxesUI(
    playerCount: Int,
    navController: NavHostController,
    viewModel: DotsAndBoxesVM = koinViewModel()
) {
    androidx.compose.runtime.LaunchedEffect(Unit) {
        viewModel.setPlayerCount(playerCount)
    }

    val gridSize = 5
    val winner = viewModel.winner
    val currentPlayer = viewModel.currentPlayer

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = UIStrings.DOTS_AND_BOXES,
                        fontWeight = FontWeight.Black,
                        fontSize = 24.sp,
                        color = AppColor.CARD_TEXT_BOX_YELLOW
                    )
                },
                navigationIcon = {
                    Box(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .size(40.dp)
                            .border(2.dp, AppColor.CARD_TEXT_BOX_YELLOW, RoundedCornerShape(4.dp))
                            .clickable { navController.popBackStack() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = UIStrings.BACK,
                            tint = AppColor.CARD_TEXT_BOX_YELLOW
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Toggle Sound */ }) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "Sound",
                            tint = AppColor.CARD_TEXT_BOX_YELLOW,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFFFF9C4)
                )
            )
        },
        bottomBar = {
            DotsBottomBar(viewModel.blueScore, viewModel.redScore)
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFFDE7),
                            Color(0xFFFFF9C4)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Game Board
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .pointerInput(Unit) {
                                detectTapGestures { offset ->
                                    val canvasWidth = size.width.toFloat()
                                    val canvasHeight = size.height.toFloat()
                                    val stepX = canvasWidth / gridSize
                                    val stepY = canvasHeight / gridSize
                                    val threshold = 20.dp.toPx()

                                    // Check horizontal lines
                                    for (r in 0..gridSize) {
                                        for (c in 0 until gridSize) {
                                            val lineXStart = c * stepX
                                            val lineXEnd = (c + 1) * stepX
                                            val lineY = r * stepY
                                            if (offset.x >= lineXStart && offset.x <= lineXEnd &&
                                                offset.y >= lineY - threshold && offset.y <= lineY + threshold
                                            ) {
                                                viewModel.onHorizontalLineClick(r, c)
                                                return@detectTapGestures
                                            }
                                        }
                                    }

                                    // Check vertical lines
                                    for (r in 0 until gridSize) {
                                        for (c in 0..gridSize) {
                                            val lineYStart = r * stepY
                                            val lineYEnd = (r + 1) * stepY
                                            val lineX = c * stepX
                                            if (offset.y >= lineYStart && offset.y <= lineYEnd &&
                                                offset.x >= lineX - threshold && offset.x <= lineX + threshold
                                            ) {
                                                viewModel.onVerticalLineClick(r, c)
                                                return@detectTapGestures
                                            }
                                        }
                                    }
                                }
                            }
                    ) {
                        val canvasWidth = this.size.width
                        val canvasHeight = this.size.height
                        val stepX = canvasWidth / gridSize
                        val stepY = canvasHeight / gridSize

                        // Draw boxes
                        for (r in 0 until gridSize) {
                            for (c in 0 until gridSize) {
                                val owner = viewModel.boxes[r][c]
                                if (owner != null) {
                                    drawRect(
                                        color = if (owner == Player.RED) Color(0xFFEA2F03).copy(alpha = 0.3f) else Color(0xFF38CEFF).copy(alpha = 0.3f),
                                        topLeft = Offset(c * stepX, r * stepY),
                                        size = Size(stepX, stepY)
                                    )
                                }
                            }
                        }

                        // Draw horizontal lines
                        for (r in 0..gridSize) {
                            for (c in 0 until gridSize) {
                                val owner = viewModel.horizontalLines[r][c]
                                val color = when (owner) {
                                    Player.RED -> Color(0xFFEA2F03)
                                    Player.BLUE -> Color(0xFF38CEFF)
                                    else -> Color.Gray.copy(alpha = 0.2f)
                                }
                                drawLine(
                                    color = color,
                                    start = Offset(c * stepX, r * stepY),
                                    end = Offset((c + 1) * stepX, r * stepY),
                                    strokeWidth = 6.dp.toPx(),
                                    cap = StrokeCap.Round
                                )
                            }
                        }

                        // Draw vertical lines
                        for (r in 0 until gridSize) {
                            for (c in 0..gridSize) {
                                val owner = viewModel.verticalLines[r][c]
                                val color = when (owner) {
                                    Player.RED -> Color(0xFFEA2F03)
                                    Player.BLUE -> Color(0xFF38CEFF)
                                    else -> Color.Gray.copy(alpha = 0.2f)
                                }
                                drawLine(
                                    color = color,
                                    start = Offset(c * stepX, r * stepY),
                                    end = Offset(c * stepX, (r + 1) * stepY),
                                    strokeWidth = 6.dp.toPx(),
                                    cap = StrokeCap.Round
                                )
                            }
                        }

                        // Draw dots
                        for (r in 0..gridSize) {
                            for (c in 0..gridSize) {
                                drawCircle(
                                    color = Color.Black,
                                    radius = 6.dp.toPx(),
                                    center = Offset(c * stepX, r * stepY)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                // Status Message
                if (winner != null) {
                    Surface(
                        modifier = Modifier
                            .clickable { viewModel.resetGame() }
                            .border(2.dp, Color.Black, RoundedCornerShape(12.dp)),
                        color = if (winner == Player.BLUE) Color(0xFF38CEFF) else Color(0xFFEA2F03),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = if (winner == Player.BLUE) "BLUE WINS!" else "RED WINS!",
                            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                } else {
                    Text(
                        text = if (currentPlayer == Player.RED) "RED'S TURN" else "BLUE'S TURN",
                        color = if (currentPlayer == Player.RED) Color(0xFFEA2F03) else Color(0xFF38CEFF),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }
        }
    }
}

@Composable
fun DotsBottomBar(blueScore: Int, redScore: Int) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        color = Color(0xFFFFF9C4)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ScoreBox(score = blueScore, color = Color(0xFF38CEFF), textColor = Color(0xFF38CEFF))
            ScoreBox(score = redScore, color = Color(0xFFEA2F03), textColor = Color(0xFFEA2F03))
        }
    }
}

@Composable
fun ScoreBox(score: Int, color: Color, textColor: Color) {
    Surface(
        modifier = Modifier
            .width(80.dp)
            .height(45.dp),
        color = Color(0xFF2E3B62),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = score.toString(),
                color = textColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}
