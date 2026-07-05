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
import com.yasinmoridi.miniverse.presentation.components.MultiPlayerHeader
import com.yasinmoridi.miniverse.utils.AppColor
import com.yasinmoridi.miniverse.utils.AppColor.BLUE_PLAYER_BDG
import com.yasinmoridi.miniverse.utils.AppColor.BLUE_PLAYER_BTN
import com.yasinmoridi.miniverse.utils.AppColor.GREEN_PLAYER_BDG
import com.yasinmoridi.miniverse.utils.AppColor.GREEN_PLAYER_BTN
import com.yasinmoridi.miniverse.utils.AppColor.GR_BG
import com.yasinmoridi.miniverse.utils.AppColor.RED_PLAYER_BDG
import com.yasinmoridi.miniverse.utils.AppColor.RED_PLAYER_BTN
import com.yasinmoridi.miniverse.utils.AppColor.Yello_PLAYER_BDG
import com.yasinmoridi.miniverse.utils.AppColor.Yello_PLAYER_BTN

@Composable
fun TypeUI() {

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GR_BG)
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MultiPlayerHeader()
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                PlayerOptionCard(
                    playerCount = 1,
                    text = "PLAYER",
                    badgeColor = RED_PLAYER_BDG,
                    buttonColor = RED_PLAYER_BTN,
                    isLeftAligned = false
                )

                Spacer(Modifier.height(40.dp))

                PlayerOptionCard(
                    playerCount = 2,
                    text = "PLAYERS",
                    badgeColor = BLUE_PLAYER_BDG,
                    buttonColor = BLUE_PLAYER_BTN,
                    isLeftAligned = true
                )

                Spacer(Modifier.height(40.dp))

                PlayerOptionCard(
                    playerCount = 3,
                    text = "PLAYERS",
                    badgeColor = GREEN_PLAYER_BDG,
                    buttonColor = GREEN_PLAYER_BTN,
                    isLeftAligned = false
                )

                Spacer(Modifier.height(40.dp))

                PlayerOptionCard(
                    playerCount = 4,
                    text = "PLAYERS",
                    badgeColor = Yello_PLAYER_BDG,
                    buttonColor = Yello_PLAYER_BTN,
                    isLeftAligned = true
                )

                Spacer(modifier = Modifier.height(120.dp))
            }
        }

        // 🟢 BUTTON (ثابت پایین)
        ShareWithFriendsButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        )
    }
}

@Composable
fun PlayerOptionCard(
    playerCount: Int,
    text: String,
    badgeColor: Color,
    buttonColor: Color,
    isLeftAligned: Boolean
) {

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = if (isLeftAligned) Alignment.CenterStart else Alignment.CenterEnd
    ) {

        // CARD اصلی
        Box(
            modifier = Modifier
                .width(280.dp)
                .height(140.dp)
                .offset(x = if (isLeftAligned) 30.dp else (-30).dp)
                .clip(RoundedCornerShape(70.dp))
                .background(Color(0xFF2E3B62))
        ) {

            // خطوط تزئینی
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 45.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Box(Modifier.width(35.dp).height(4.dp).background(Color.White.copy(alpha = 0.2f)))
                    Box(Modifier.width(35.dp).height(4.dp).background(Color.White.copy(alpha = 0.2f)))
                }
                Spacer(Modifier.height(6.dp))
                Box(Modifier.width(90.dp).height(4.dp).background(Color.White.copy(alpha = 0.2f)))
            }

            // دکمه پایین
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(14.dp)
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(26.dp))
                    .background(buttonColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    color = Color.White,
                    fontWeight = FontWeight.Black,
                    fontSize = 20.sp
                )
            }
        }

        // 🔴 BADGE (نصف بیرون، نصف داخل)
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-20).dp) // 👈 نصف بیرون کارت
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

@Composable
fun ShareWithFriendsButton(modifier: Modifier = Modifier) {
    Button(
        onClick = { /* Implement share functionality here */ },
        modifier = modifier
            .fillMaxWidth(0.85f)
            .height(64.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1BB632)), // Bright Green
        shape = RoundedCornerShape(32.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Share",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                "SHARE WITH FRIENDS!",
                color = Color.White,
                fontWeight = FontWeight.Black,
                fontSize = 18.sp
            )
        }
    }
}
