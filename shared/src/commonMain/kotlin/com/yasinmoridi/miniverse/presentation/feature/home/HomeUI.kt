package com.yasinmoridi.miniverse.presentation.feature.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.yasinmoridi.miniverse.presentation.core.navigation.AppDestination
import com.yasinmoridi.miniverse.utils.AppColor
import com.yasinmoridi.miniverse.utils.UIStrings
import com.yasinmoridi.miniverse.utils.mouseDragScroll
import miniverse.shared.generated.resources.Res
import miniverse.shared.generated.resources.img_sword
import org.jetbrains.compose.resources.painterResource

data class Game(
    val name: String, 
    val color: Color, 
    val supportedPlayerCounts: List<Int> = listOf(1, 2)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeUI(
    playerCount: Int,
    navController: NavHostController
) {
    val gridState = rememberLazyGridState()
    
    // All possible games
    val allGames = remember {
        listOf(
            Game(UIStrings.TIC_TAC_TOE, AppColor.CARD_TEXT_BOX_CYAN, listOf(1, 2)),
            Game(UIStrings.MINESWEEPER, AppColor.CARD_TEXT_BOX_ORANGE, listOf(1)),
            Game(UIStrings.OTHELLO, Color(0xFF2E6B2A), listOf(1, 2)),
            Game(UIStrings.METHELLO, Color(0xFF6B2E2A), listOf(1, 2)),
        )
    }

    // Filter games based on selected playerCount
    val filteredGames = remember(playerCount) {
        allGames.filter { game -> 
            playerCount in game.supportedPlayerCounts 
        } + Game(UIStrings.COMING_SOON, Color.Gray, listOf(1, 2, 3, 4)) // Always show Coming Soon
    }

    Scaffold(
        containerColor = AppColor.HOME_BG,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = UIStrings.playersGames(playerCount),
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
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        bottomBar = {
            BottomScoreBar()
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = gridState,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .mouseDragScroll(gridState),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(filteredGames) { game ->
                GameCard(game) {
                    if (game.name != UIStrings.COMING_SOON) {
                        navController.navigate(AppDestination.GameInfo(game.name, playerCount))
                    }
                }
            }
        }
    }
}

@Composable
fun GameCard(game: Game, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .aspectRatio(0.75f)
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, AppColor.CARD_BORDER),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Placeholder for Game Illustration
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(Res.drawable.img_sword), // Using sword as placeholder
                    contentDescription = game.name,
                    modifier = Modifier.fillMaxSize(0.7f),
                    contentScale = ContentScale.Fit
                )
            }

            // Game Title Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(game.color),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = game.name,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 14.sp,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }
        }
    }
}

@Composable
fun BottomScoreBar() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        color = Color(0xFFD1E9FF), // Light blue bar at the bottom
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 40.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Player 1 (Red)
            ScoreAvatar(color = Color(0xFFFF5252), score = 0)
            
            // Player 2 (Blue)
            ScoreAvatar(color = Color(0xFF00B0FF), score = 0)
        }
    }
}

@Composable
fun ScoreAvatar(color: Color, score: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(color),
            contentAlignment = Alignment.Center
        ) {
            // Avatar Placeholder
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.3f))
            )
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Surface(
            modifier = Modifier
                .width(50.dp)
                .height(24.dp),
            color = Color.White,
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, color)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = score.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
        }
    }
}
