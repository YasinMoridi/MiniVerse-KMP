package com.yasinmoridi.miniverse.presentation.feature.tic_tac_toe

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.VolumeUp
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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.yasinmoridi.miniverse.presentation.components.GameOverPopup
import com.yasinmoridi.miniverse.utils.AppColor
import com.yasinmoridi.miniverse.utils.UIStrings
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicTacToeUI(
    playerCount: Int,
    navController: NavHostController,
    viewModel: TicTacToeVM = koinViewModel()
) {
    androidx.compose.runtime.LaunchedEffect(Unit) {
        viewModel.setPlayerCount(playerCount)
    }
    val board = viewModel.board
    val winner = viewModel.winner
    val isDraw = viewModel.isDraw
    val currentPlayer = viewModel.currentPlayer

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = UIStrings.TIC_TAC_TOE,
                        fontWeight = FontWeight.Black,
                        fontSize = 24.sp,
                        color = Color(0xFF534BAE)
                    )
                },
                navigationIcon = {
                    Box(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .size(40.dp)
                            .border(2.dp, Color(0xFF534BAE), RoundedCornerShape(8.dp))
                            .clickable { navController.popBackStack() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = UIStrings.BACK,
                            tint = Color(0xFF534BAE)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Toggle Sound */ }) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "Sound",
                            tint = Color(0xFF534BAE),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFE3DFFF)
                )
            )
        },
        bottomBar = {
            BottomBar(viewModel.blueScore, viewModel.redScore)
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF6B7FD7),
                            Color(0xFF9E48EB)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Current Turn Indicator
                AnimatedTurnIndicator(currentPlayer, winner == null && !isDraw)

                Spacer(modifier = Modifier.height(20.dp))

                // Game Board
                Box(
                    modifier = Modifier
                        .size(320.dp)
                        .shadow(16.dp, RoundedCornerShape(16.dp))
                        .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {
                    // Grid Lines with animation
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val strokeWidth = 8.dp.toPx()
                        val color = Color.Black.copy(alpha = 0.8f)
                        
                        // Vertical lines
                        drawLine(
                            color = color,
                            start = Offset(size.width / 3, 0f),
                            end = Offset(size.width / 3, size.height),
                            strokeWidth = strokeWidth,
                            cap = StrokeCap.Round
                        )
                        drawLine(
                            color = color,
                            start = Offset(2 * size.width / 3, 0f),
                            end = Offset(2 * size.width / 3, size.height),
                            strokeWidth = strokeWidth,
                            cap = StrokeCap.Round
                        )
                        // Horizontal lines
                        drawLine(
                            color = color,
                            start = Offset(0f, size.height / 3),
                            end = Offset(size.width, size.height / 3),
                            strokeWidth = strokeWidth,
                            cap = StrokeCap.Round
                        )
                        drawLine(
                            color = color,
                            start = Offset(0f, 2 * size.height / 3),
                            end = Offset(size.width, 2 * size.height / 3),
                            strokeWidth = strokeWidth,
                            cap = StrokeCap.Round
                        )
                    }

                    // Cells
                    Column(modifier = Modifier.fillMaxSize()) {
                        repeat(3) { row ->
                            Row(modifier = Modifier.weight(1f)) {
                                repeat(3) { col ->
                                    val index = row * 3 + col
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxHeight()
                                            .clickable { viewModel.onCellClick(index) },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        androidx.compose.animation.AnimatedContent(
                                            targetState = board[index],
                                            transitionSpec = {
                                                scaleIn(animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)) togetherWith
                                                fadeOut()
                                            }
                                        ) { piece ->
                                            when (piece) {
                                                "O" -> CirclePiece()
                                                "X" -> CrossPiece()
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                // Status Message with Pop-up animation
                GameOverPopup(
                    visible = winner != null || isDraw,
                    text = when {
                        winner == Player.O -> "BLUE WINS! 🏆"
                        winner == Player.X -> "RED WINS! 🏆"
                        else -> "IT'S A DRAW! 🤝"
                    },
                    color = when {
                        winner == Player.O -> Color(0xFF38CEFF)
                        winner == Player.X -> Color(0xFFEA2F03)
                        else -> Color.Gray
                    },
                    onClick = { viewModel.resetGame() }
                )
            }
        }
    }
}

@Composable
fun AnimatedTurnIndicator(currentPlayer: Player, isGameActive: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color.Black.copy(alpha = 0.2f))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        val blueAlpha by animateFloatAsState(if (currentPlayer == Player.O && isGameActive) 1f else 0.3f)
        val redAlpha by animateFloatAsState(if (currentPlayer == Player.X && isGameActive) 1f else 0.3f)
        
        Text("BLUE", color = Color(0xFF38CEFF).copy(alpha = blueAlpha), fontWeight = FontWeight.Black)
        Spacer(modifier = Modifier.width(16.dp))
        Text("VS", color = Color.White, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(16.dp))
        Text("RED", color = Color(0xFFEA2F03).copy(alpha = redAlpha), fontWeight = FontWeight.Black)
    }
}

@Composable
fun CirclePiece() {
    val progress = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        progress.animateTo(1f, tween(500, easing = FastOutSlowInEasing))
    }
    
    Canvas(modifier = Modifier.size(60.dp)) {
        drawCircle(
            color = Color(0xFF38CEFF),
            radius = (size.minDimension / 2) * progress.value,
            style = Stroke(width = 8.dp.toPx())
        )
    }
}

@Composable
fun CrossPiece() {
    val progress1 = remember { Animatable(0f) }
    val progress2 = remember { Animatable(0f) }
    
    LaunchedEffect(Unit) {
        progress1.animateTo(1f, tween(300))
        progress2.animateTo(1f, tween(300))
    }
    
    Canvas(modifier = Modifier.size(60.dp)) {
        val strokeWidth = 8.dp.toPx()
        val color = Color(0xFFEA2F03)
        
        // First line
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width * progress1.value, size.height * progress1.value),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        
        // Second line
        if (progress1.value > 0.5f) {
            drawLine(
                color = color,
                start = Offset(size.width, 0f),
                end = Offset(size.width - (size.width * progress2.value), size.height * progress2.value),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round
            )
        }
    }
}

@Composable
fun BottomBar(blueScore: Int, redScore: Int) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        color = Color(0xFFE3DFFF)
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
