package com.yasinmoridi.miniverse.presentation.feature.game_info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.yasinmoridi.miniverse.presentation.core.navigation.AppDestination
import com.yasinmoridi.miniverse.utils.AppColor
import com.yasinmoridi.miniverse.utils.UIStrings
import miniverse.shared.generated.resources.Res
import miniverse.shared.generated.resources.img_sword
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameInfoUI(
    gameName: String,
    playerCount: Int,
    navController: NavHostController
) {
    // In a real app, we would fetch details based on gameName
    val description = "Can you find the hidden number?\nUse logic and hints to guess the\ncorrect number in the fewest tries."
    val gameColor = when (gameName) {
        UIStrings.GUESS_THE_NUMBER -> AppColor.CARD_TEXT_BOX_PURPLE
        UIStrings.TAP_COUNTER -> AppColor.CARD_TEXT_BOX_BLUE
        UIStrings.SNAKE_BITE -> AppColor.CARD_TEXT_BOX_GREEN
        UIStrings.AVOID_THE_BLOCKS -> AppColor.CARD_TEXT_BOX_RED
        UIStrings.QUIZ_MANIA -> AppColor.CARD_TEXT_BOX_PINK
        UIStrings.WORD_SCRAMBLE -> AppColor.CARD_TEXT_BOX_ORANGE
        UIStrings.BRICK_BREAKER -> AppColor.CARD_TEXT_BOX_GRAY
        UIStrings.CATCH_THE_OBJECT -> AppColor.CARD_TEXT_BOX_YELLOW
        UIStrings.TIC_TAC_TOE -> Color(0xFF534BAE)
        else -> AppColor.CARD_TEXT_BOX_CYAN
    }

    Scaffold(
        containerColor = Color(0xFFE3F2FD),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "GAME INFO",
                        fontWeight = FontWeight.Black,
                        fontSize = 20.sp,
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
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = gameName,
                color = gameColor,
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = description,
                color = Color.Black.copy(alpha = 0.8f),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.weight(0.1f))

            // Game Illustration Placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.foundation.Image(
                    painter = painterResource(Res.drawable.img_sword),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(0.8f)
                )
            }

            // Character Icon
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .padding(3.dp)
                    .clip(CircleShape)
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Text("😊", fontSize = 36.sp)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Info Row
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                shape = RoundedCornerShape(35.dp),
                color = Color.White,
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    InfoItem(icon = Icons.Default.Timer, text = "1-100", color = gameColor)
                    VerticalDivider(modifier = Modifier.height(30.dp), thickness = 1.dp, color = Color.LightGray)
                    InfoItem(icon = Icons.Default.Lightbulb, text = "Helpful Hints", color = gameColor)
                    VerticalDivider(modifier = Modifier.height(30.dp), thickness = 1.dp, color = Color.LightGray)
                    InfoItem(icon = Icons.Default.EmojiEvents, text = "Win in fewer\ntries.", color = gameColor)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Play Button
            Button(
                onClick = {
                    if (gameName == UIStrings.TIC_TAC_TOE) {
                        navController.navigate(AppDestination.TicTacToe(playerCount))
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(60.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = gameColor)
            ) {
                Text(
                    text = "PLAY",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun InfoItem(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = text,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            lineHeight = 12.sp,
            textAlign = TextAlign.Start
        )
    }
}
