package com.yasinmoridi.miniverse.presentation.feature.minesweeper

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.yasinmoridi.miniverse.presentation.components.GameOverPopup
import com.yasinmoridi.miniverse.utils.UIStrings
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MinesweeperUI(
    navController: NavHostController,
    viewModel: MinesweeperVM = koinViewModel(),
) {
    val board = viewModel.board
    val status = viewModel.status

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = UIStrings.MINESWEEPER,
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
                            .combinedClickable { navController.popBackStack() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = UIStrings.BACK,
                            tint = Color(0xFF534BAE)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFE3DFFF)
                )
            )
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
                // Grid
                Column(
                    modifier = Modifier
                        .border(4.dp, Color(0xFF2E3B62), RoundedCornerShape(8.dp))
                        .padding(4.dp)
                ) {
                    for (r in board.indices) {
                        Row {
                            for (c in board[r].indices) {
                                MinesweeperCell(
                                    cell = board[r][c],
                                    onClick = { viewModel.onCellClick(r, c) }
                                ) { viewModel.onCellLongClick(r, c) }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                GameOverPopup(
                    visible = status != GameStatus.IN_PROGRESS,
                    text = if (status == GameStatus.WON) "YOU WIN! 💣" else "BOOM! 💥",
                    color = if (status == GameStatus.WON) Color(0xFF4CAF50) else Color(0xFFF44336),
                    onClick = { viewModel.resetGame() }
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MinesweeperCell(
    cell: Cell,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .size(32.dp)
            .padding(1.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        shape = RoundedCornerShape(2.dp),
        color = when {
            cell.isRevealed -> if (cell.isMine) Color.Red else Color.LightGray
            cell.isFlagged -> Color.Yellow
            else -> Color(0xFF534BAE)
        }
    ) {
        Box(contentAlignment = Alignment.Center) {
            if (cell.isRevealed && !cell.isMine && (cell.neighborMines > 0)) {
                Text(
                    text = cell.neighborMines.toString(),
                    color = getNeighborColor(cell.neighborMines),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            } else if (cell.isRevealed && cell.isMine) {
                Text("💣", fontSize = 18.sp)
            } else if (cell.isFlagged) {
                Text("🚩", fontSize = 18.sp)
            }
        }
    }
}

private fun getNeighborColor(count: Int): Color {
    return when (count) {
        1 -> Color.Blue
        2 -> Color(0xFF008000)
        3 -> Color.Red
        4 -> Color(0xFF000080)
        5 -> Color(0xFF800000)
        6 -> Color(0xFF008080)
        7 -> Color.Black
        8 -> Color.Gray
        else -> Color.Black
    }
}
