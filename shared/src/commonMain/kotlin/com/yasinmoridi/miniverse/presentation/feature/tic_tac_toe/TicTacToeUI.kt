package com.yasinmoridi.miniverse.presentation.feature.tic_tac_toe

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.yasinmoridi.miniverse.utils.AppColor
import com.yasinmoridi.miniverse.utils.UIStrings
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicTacToeUI(
    navController: NavHostController,
    viewModel: TicTacToeVM = koinViewModel()
) {
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
                // Game Board
                Box(
                    modifier = Modifier
                        .size(300.dp)
                        .padding(16.dp)
                ) {
                    // Grid Lines
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val strokeWidth = 8.dp.toPx()
                        // Vertical lines
                        drawLine(
                            color = Color.Black,
                            start = Offset(size.width / 3, 0f),
                            end = Offset(size.width / 3, size.height),
                            strokeWidth = strokeWidth,
                            cap = StrokeCap.Round
                        )
                        drawLine(
                            color = Color.Black,
                            start = Offset(2 * size.width / 3, 0f),
                            end = Offset(2 * size.width / 3, size.height),
                            strokeWidth = strokeWidth,
                            cap = StrokeCap.Round
                        )
                        // Horizontal lines
                        drawLine(
                            color = Color.Black,
                            start = Offset(0f, size.height / 3),
                            end = Offset(size.width, size.height / 3),
                            strokeWidth = strokeWidth,
                            cap = StrokeCap.Round
                        )
                        drawLine(
                            color = Color.Black,
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
                                        if (board[index] == "O") {
                                            CirclePiece()
                                        } else if (board[index] == "X") {
                                            CrossPiece()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                // Status Message
                if (winner != null || isDraw) {
                    Surface(
                        modifier = Modifier
                            .clickable { viewModel.resetGame() }
                            .border(2.dp, Color.Black, RoundedCornerShape(12.dp)),
                        color = if (winner == Player.O) Color(0xFF38CEFF) else if (winner == Player.X) Color(0xFFEA2F03) else Color.Gray,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = when {
                                winner == Player.O -> "BLUE WINS!"
                                winner == Player.X -> "RED WINS!"
                                else -> "DRAW!"
                            },
                            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CirclePiece() {
    Canvas(modifier = Modifier.size(60.dp)) {
        drawCircle(
            color = Color(0xFF38CEFF),
            radius = size.minDimension / 2,
            style = Stroke(width = 6.dp.toPx())
        )
    }
}

@Composable
fun CrossPiece() {
    Canvas(modifier = Modifier.size(60.dp)) {
        val strokeWidth = 6.dp.toPx()
        drawLine(
            color = Color(0xFFEA2F03),
            start = Offset(0f, 0f),
            end = Offset(size.width, size.height),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = Color(0xFFEA2F03),
            start = Offset(size.width, 0f),
            end = Offset(0f, size.height),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
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
