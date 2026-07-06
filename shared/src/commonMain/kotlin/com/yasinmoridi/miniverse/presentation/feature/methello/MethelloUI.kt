package com.yasinmoridi.miniverse.presentation.feature.methello

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.yasinmoridi.miniverse.presentation.components.GameOverPopup
import com.yasinmoridi.miniverse.presentation.feature.othello.OthelloPiece
import com.yasinmoridi.miniverse.presentation.feature.othello.OthelloPieceItem
import com.yasinmoridi.miniverse.presentation.feature.othello.OthelloBottomBar
import com.yasinmoridi.miniverse.utils.UIStrings
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MethelloUI(
    playerCount: Int,
    navController: NavHostController,
    viewModel: MethelloVM = koinViewModel()
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
                        text = UIStrings.METHELLO,
                        fontWeight = FontWeight.Black,
                        fontSize = 24.sp,
                        color = Color(0xFFAD4B53)
                    )
                },
                navigationIcon = {
                    Box(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .size(40.dp)
                            .border(2.dp, Color(0xFFAD4B53), RoundedCornerShape(4.dp))
                            .clickable { navController.popBackStack() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = UIStrings.BACK,
                            tint = Color(0xFFAD4B53)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Toggle Sound */ }) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "Sound",
                            tint = Color(0xFFAD4B53),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFFFE3DF)
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
                            Color(0xFF5A2D27),
                            Color(0xFF3B1B18)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Game Board (6x6)
                Box(
                    modifier = Modifier
                        .size(300.dp)
                        .padding(8.dp)
                        .background(Color(0xFF240D0B), RoundedCornerShape(4.dp))
                        .padding(4.dp)
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        repeat(6) { row ->
                            Row(modifier = Modifier.weight(1f)) {
                                repeat(6) { col ->
                                    val isHint = validMoves.any { it.first == row && it.second == col }
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxHeight()
                                            .padding(1.dp)
                                            .background(Color(0xFF6B2E2A))
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
