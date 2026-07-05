package com.yasinmoridi.miniverse.presentation.feature.type

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.yasinmoridi.miniverse.presentation.components.MultiPlayerHeader
import com.yasinmoridi.miniverse.presentation.core.navigation.AppDestination
import com.yasinmoridi.miniverse.utils.AppColor
import com.yasinmoridi.miniverse.utils.AppColor.BLUE_PLAYER_BDG
import com.yasinmoridi.miniverse.utils.AppColor.BLUE_PLAYER_BTN
import com.yasinmoridi.miniverse.utils.AppColor.DARK_NAVY
import com.yasinmoridi.miniverse.utils.AppColor.GREEN_PLAYER_BDG
import com.yasinmoridi.miniverse.utils.AppColor.GREEN_PLAYER_BTN
import com.yasinmoridi.miniverse.utils.AppColor.GR_BG
import com.yasinmoridi.miniverse.utils.AppColor.LINE_DECORATION
import com.yasinmoridi.miniverse.utils.AppColor.RED_PLAYER_BDG
import com.yasinmoridi.miniverse.utils.AppColor.RED_PLAYER_BTN
import com.yasinmoridi.miniverse.utils.AppColor.SHARE_BTN_GREEN
import com.yasinmoridi.miniverse.utils.AppColor.YELLOW_PLAYER_BDG
import com.yasinmoridi.miniverse.utils.AppColor.YELLOW_PLAYER_BTN
import com.yasinmoridi.miniverse.utils.UIStrings

@Composable
fun TypeUI(navController: NavHostController) {

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GR_BG),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 600.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(60.dp))

            MultiPlayerHeader()

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(Modifier.height(30.dp))

                PlayerOptionCard(
                    playerCount = 1,
                    text = UIStrings.PLAYER,
                    badgeColor = RED_PLAYER_BDG,
                    buttonColor = RED_PLAYER_BTN,
                    isLeftAligned = false,
                    onClick = { navController.navigate(AppDestination.Home(1)) }
                )

                Spacer(Modifier.height(40.dp))

                PlayerOptionCard(
                    playerCount = 2,
                    text = UIStrings.PLAYERS,
                    badgeColor = BLUE_PLAYER_BDG,
                    buttonColor = BLUE_PLAYER_BTN,
                    isLeftAligned = true,
                    onClick = { navController.navigate(AppDestination.Home(2)) }
                )

                Spacer(Modifier.height(40.dp))

                PlayerOptionCard(
                    playerCount = 3,
                    text = UIStrings.PLAYERS,
                    badgeColor = GREEN_PLAYER_BDG,
                    buttonColor = GREEN_PLAYER_BTN,
                    isLeftAligned = false,
                    onClick = { navController.navigate(AppDestination.Home(3)) }
                )

                Spacer(Modifier.height(40.dp))

                PlayerOptionCard(
                    playerCount = 4,
                    text = UIStrings.PLAYERS,
                    badgeColor = YELLOW_PLAYER_BDG,
                    buttonColor = YELLOW_PLAYER_BTN,
                    isLeftAligned = true,
                    onClick = { navController.navigate(AppDestination.Home(4)) }
                )

                Spacer(modifier = Modifier.height(120.dp))
            }
        }

        // 🟢 BUTTON (ثابت پایین)
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .widthIn(max = 600.dp)
                .padding(bottom = 32.dp),
            contentAlignment = Alignment.Center
        ) {
            ShareWithFriendsButton()
        }
    }
}

@Composable
fun PlayerOptionCard(
    playerCount: Int,
    text: String,
    badgeColor: Color,
    buttonColor: Color,
    isLeftAligned: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = if (isLeftAligned) Alignment.CenterStart else Alignment.CenterEnd
    ) {
        // 🟢 Wrapper for Card and Badge to keep them perfectly aligned horizontally
        Box(
            modifier = Modifier
                .width(280.dp)
                .offset(x = if (isLeftAligned) 30.dp else (-30).dp),
            contentAlignment = Alignment.TopCenter
        ) {
            // CARD اصلی
            Surface(
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(70.dp)),
                color = DARK_NAVY
            ) {
                Box {
                    // خطوط تزئینی
                    Column(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 45.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Box(
                                Modifier.width(35.dp).height(4.dp)
                                    .background(LINE_DECORATION)
                            )
                            Box(
                                Modifier.width(35.dp).height(4.dp)
                                    .background(LINE_DECORATION)
                            )
                        }
                        Spacer(Modifier.height(6.dp))
                        Box(Modifier.width(90.dp).height(4.dp).background(LINE_DECORATION))
                    }

                    // دکمه پایین (اکنون بخشی از کل کارت است اما بصری جدا می‌ماند)
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 16.dp)
                            .fillMaxWidth(0.8f)
                            .height(50.dp)
                            .clip(RoundedCornerShape(25.dp))
                            .background(buttonColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = text,
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            fontSize = 18.sp
                        )
                    }
                }
            }

            // 🔴 BADGE (نصف بیرون، نصف داخل) - اکنون نسبت به مرکز Wrapper تنظیم شده است
            Box(
                modifier = Modifier
                    .offset(y = (-30).dp)
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(badgeColor),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.15f))
                )
                Text(
                    text = playerCount.toString(),
                    color = Color.White,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Black
                )
            }
        }
    }
}

@Composable
fun ShareWithFriendsButton(modifier: Modifier = Modifier) {
    Button(
        onClick = { /* Implement share functionality here */ },
        modifier = modifier
            .fillMaxWidth(0.85f)
            .height(64.dp),
        colors = ButtonDefaults.buttonColors(containerColor = SHARE_BTN_GREEN), // Bright Green
        shape = RoundedCornerShape(32.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = UIStrings.SHARE_WITH_FRIENDS,
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                UIStrings.SHARE_WITH_FRIENDS,
                color = Color.White,
                fontWeight = FontWeight.Black,
                fontSize = 18.sp
            )
        }
    }
}
