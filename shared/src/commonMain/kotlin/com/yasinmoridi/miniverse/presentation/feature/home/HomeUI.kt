package com.yasinmoridi.miniverse.presentation.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.yasinmoridi.miniverse.utils.AppColor.BLUE_PLAYER_BTN
import com.yasinmoridi.miniverse.utils.AppColor.DARK_NAVY
import com.yasinmoridi.miniverse.utils.AppColor.GREEN_PLAYER_BTN
import com.yasinmoridi.miniverse.utils.AppColor.GR_BG
import com.yasinmoridi.miniverse.utils.AppColor.RED_PLAYER_BTN
import com.yasinmoridi.miniverse.utils.AppColor.YELLOW_PLAYER_BTN
import com.yasinmoridi.miniverse.utils.UIStrings

data class Game(val name: String, val description: String, val color: Color)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeUI(
    playerCount: Int,
    navController: NavHostController
) {
    val games = when (playerCount) {
        1 -> listOf(
            Game("Solitaire", "Classic single player card game", RED_PLAYER_BTN),
            Game("2048", "Slide tiles to reach 2048", RED_PLAYER_BTN),
            Game("Sudoku", "Number placement puzzle", RED_PLAYER_BTN)
        )
        2 -> listOf(
            Game("Chess", "Strategic board game", BLUE_PLAYER_BTN),
            Game("Checkers", "Capture your opponent's pieces", BLUE_PLAYER_BTN),
            Game("Tic Tac Toe", "Get three in a row", BLUE_PLAYER_BTN)
        )
        3 -> listOf(
            Game("Ludo", "Race your tokens home", GREEN_PLAYER_BTN),
            Game("Uno", "Be the first to get rid of cards", GREEN_PLAYER_BTN),
            Game("Hearts", "Avoid hearts and the Queen of Spades", GREEN_PLAYER_BTN)
        )
        4 -> listOf(
            Game("Monopoly", "Real estate trading game", YELLOW_PLAYER_BTN),
            Game("Carrom", "Strike and pocket coins", YELLOW_PLAYER_BTN),
            Game("Bridge", "Trick-taking card game", YELLOW_PLAYER_BTN)
        )
        else -> emptyList()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GR_BG)
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(UIStrings.playersGames(playerCount), fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = UIStrings.BACK)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            },
            containerColor = Color.Transparent
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(games) { game ->
                    GameItem(game)
                }
            }
        }
    }
}

@Composable
fun GameItem(game: Game) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(DARK_NAVY)
            .clickable { /* Launch Game */ }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(game.color)
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column {
            Text(
                text = game.name,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = game.description,
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 14.sp
            )
        }
    }
}
