package com.yasinmoridi.miniverse.presentation.feature.othello

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.yasinmoridi.miniverse.presentation.components.GameOverPopup
import com.yasinmoridi.miniverse.utils.UIStrings
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OthelloUI(
    playerCount: Int,
    navController: NavHostController,
    viewModel: OthelloVM = koinViewModel()
) {
    androidx.compose.runtime.LaunchedEffect(Unit) {
        viewModel.setPlayerCount(playerCount)
    }
    
    val board = viewModel.board
    val currentPlayer = viewModel.currentPlayer
    val blackScore = viewModel.blackScore
    val whiteScore = viewModel.whiteScore
    val winner = viewModel.winner
    val gameOver = viewModel.gameOver
    val validMoves = viewModel.validMoves

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = UIStrings.OTHELLO,
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
                            .border(2.dp, Color(0xFF534BAE), RoundedCornerShape(4.dp))
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
            OthelloBottomBar(blackScore, whiteScore)
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF2D5A27),
                            Color(0xFF1B3B18)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Game Board
                Box(
                    modifier = Modifier
                        .size(320.dp)
                        .padding(8.dp)
                        .background(Color(0xFF0D240B), RoundedCornerShape(4.dp))
                        .padding(4.dp)
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        repeat(8) { row ->
                            Row(modifier = Modifier.weight(1f)) {
                                repeat(8) { col ->
                                    val isHint = validMoves.any { it.first == row && it.second == col }
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxHeight()
                                            .padding(1.dp)
                                            .background(Color(0xFF2E6B2A))
                                            .clickable { viewModel.onCellClick(row, col) },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (board[row][col] != OthelloPiece.NONE) {
                                            OthelloPieceItem(
                                                piece = board[row][col]
                                            )
                                        } else if (isHint && !gameOver) {
                                            Box(
                                                modifier = Modifier
                                                    .size(10.dp)
                                                    .clip(CircleShape)
                                                    .background(Color.Black.copy(alpha = 0.2f))
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Current Turn
                if (!gameOver) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "TURN: ",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(if (currentPlayer == OthelloPiece.BLACK) Color.Black else Color.White)
                                .border(1.dp, Color.Gray, CircleShape)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Game Over Message
                GameOverPopup(
                    visible = gameOver,
                    text = when {
                        winner == OthelloPiece.BLACK -> "BLACK WINS! 🏆"
                        winner == OthelloPiece.WHITE -> "WHITE WINS! 🏆"
                        else -> "DRAW! 🤝"
                    },
                    color = when {
                        winner == OthelloPiece.BLACK -> Color.Black
                        winner == OthelloPiece.WHITE -> Color.White
                        else -> Color.Gray
                    },
                    onClick = { viewModel.resetGame() }
                )
            }
        }
    }
}

@Composable
fun OthelloPieceItem(piece: OthelloPiece) {
    val targetColor = if (piece == OthelloPiece.BLACK) Color.Black else Color.White
    val animatedColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = tween(durationMillis = 400)
    )

    // Flip animation
    val rotation by animateFloatAsState(
        targetValue = if (piece == OthelloPiece.BLACK) 0f else 180f,
        animationSpec = tween(durationMillis = 400)
    )
    
    // Scale animation for placement
    val scale = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        scale.animateTo(1f, tween(durationMillis = 300))
    }

    Box(
        modifier = Modifier
            .fillMaxSize(0.85f)
            .graphicsLayer {
                rotationY = rotation
                scaleX = scale.value
                scaleY = scale.value
            }
            .clip(CircleShape)
            .background(animatedColor)
            .border(1.dp, Color.Gray, CircleShape)
    )
}

@Composable
fun OthelloBottomBar(blackScore: Int, whiteScore: Int) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        color = Color(0xFFE3DFFF)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OthelloScoreBox(score = blackScore, color = Color.Black)
            OthelloScoreBox(score = whiteScore, color = Color.White)
        }
    }
}

@Composable
fun OthelloScoreBox(score: Int, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(color)
                .border(1.dp, Color.Gray, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Surface(
            modifier = Modifier
                .width(60.dp)
                .height(40.dp),
            color = Color(0xFF2E3B62),
            shape = RoundedCornerShape(8.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = score.toString(),
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black
                )
            }
        }
    }
}
