package com.yasinmoridi.miniverse.presentation.feature.avoid_the_blocks

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.yasinmoridi.miniverse.utils.AppColor
import com.yasinmoridi.miniverse.utils.UIStrings
import kotlinx.coroutines.delay
import miniverse.shared.generated.resources.Res
import miniverse.shared.generated.resources.img_rocket
import miniverse.shared.generated.resources.img_space_bg
import org.jetbrains.compose.resources.painterResource
import kotlin.random.Random

class Block(
    val id: Int,
    var x: Float,
    var y: Float,
    var value: Int,
    val color: Color
) {
    val scale = Animatable(0f)
}

data class Bullet(
    val id: Int,
    var x: Float,
    var y: Float
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvoidTheBlocksUI(
    playerCount: Int,
    navController: NavHostController
) {
    var score by remember { mutableStateOf(0) }
    var bestScore by remember { mutableStateOf(49) }
    var isGameOver by remember { mutableStateOf(false) }
    
    var rocketTargetX by remember { mutableStateOf(0.5f) }
    val rocketX by animateFloatAsState(
        targetValue = rocketTargetX,
        animationSpec = spring(stiffness = Spring.StiffnessHigh),
        label = "RocketX"
    )

    // Calculate rotation based on movement
    var lastRocketX by remember { mutableStateOf(0.5f) }
    val rotation by animateFloatAsState(
        targetValue = (rocketTargetX - lastRocketX) * 400f,
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "Rotation"
    )
    
    LaunchedEffect(rocketTargetX) {
        delay(50)
        lastRocketX = rocketTargetX
    }

    val blocks = remember { mutableStateListOf<Block>() }
    val bullets = remember { mutableStateListOf<Bullet>() }
    var frame by remember { mutableStateOf(0) }
    var bulletIdCounter by remember { mutableStateOf(0) }

    // Game Loop
    LaunchedEffect(isGameOver) {
        if (isGameOver) return@LaunchedEffect
        
        var blockIdCounter = 0
        while (!isGameOver) {
            delay(16)
            frame++
            
            // Spawn blocks - Difficulty increases
            val spawnRate = (50 - (score / 4)).coerceAtLeast(25)
            if (frame % spawnRate == 0) {
                val newBlock = Block(
                    id = blockIdCounter++,
                    x = Random.nextFloat().coerceIn(0.1f, 0.9f),
                    y = -0.1f,
                    value = Random.nextInt(1, 10 + (score / 8)),
                    color = listOf(
                        Color(0xFFFF5252), Color(0xFFFFD740), 
                        Color(0xFF69F0AE), Color(0xFF40C4FF), 
                        Color(0xFFE040FB), Color(0xFFFF6E40)
                    ).random()
                )
                blocks.add(newBlock)
            }

            // Automatic Shooting
            if (frame % 10 == 0) {
                bullets.add(Bullet(bulletIdCounter++, rocketX, 0.88f))
            }
            
            // Move blocks
            val blockIterator = blocks.iterator()
            while (blockIterator.hasNext()) {
                val block = blockIterator.next()
                block.y += (0.006f + (score * 0.0001f)).coerceAtMost(0.015f)
                
                // Collision check with Rocket (Rocket is at y=0.92)
                if (block.y in 0.88f..0.95f) {
                    val rocketRange = (rocketX - 0.12f)..(rocketX + 0.12f) // Visual-based hitbox
                    if (block.x in rocketRange) {
                        isGameOver = true
                    }
                }
                
                // Lose if block reaches ground
                if (block.y > 1.0f) {
                    isGameOver = true
                }
            }

            // Move bullets and check collision with blocks
            val bulletIterator = bullets.iterator()
            while (bulletIterator.hasNext()) {
                val bullet = bulletIterator.next()
                bullet.y -= 0.03f
                
                if (bullet.y < -0.1f) {
                    bulletIterator.remove()
                    continue
                }

                // Bullet-Block Collision
                val hittingBlock = blocks.find { block ->
                    val dx = kotlin.math.abs(block.x - bullet.x)
                    val dy = kotlin.math.abs(block.y - bullet.y)
                    dx < 0.1f && dy < 0.06f
                }

                if (hittingBlock != null) {
                    bulletIterator.remove()
                    hittingBlock.value -= 1
                    
                    if (hittingBlock.value <= 0) {
                        blocks.remove(hittingBlock)
                        score++
                    }
                }
            }
            
            if (score > bestScore) bestScore = score
        }
    }

    Scaffold(
        containerColor = Color(0xFFE3F2FD),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = UIStrings.AVOID_THE_BLOCKS,
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
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = null,
                            tint = AppColor.DARK_NAVY,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Score Board
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ScoreChip(label = "SCORE", value = score)
                ScoreChip(label = "BEST", value = bestScore)
            }

            // Game Area
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            val newX = rocketTargetX + (dragAmount.x / size.width)
                            rocketTargetX = newX.coerceIn(0.05f, 0.95f)
                        }
                    }
            ) {
                // Game Board Background Image
                Image(
                    painter = painterResource(Res.drawable.img_space_bg),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                
                // Draw Bullets
                bullets.forEach { bullet ->
                    Box(
                        modifier = Modifier
                            .offset {
                                IntOffset(
                                    (bullet.x * maxWidth.toPx()).toInt() - 4.dp.toPx().toInt(),
                                    (bullet.y * maxHeight.toPx()).toInt()
                                )
                            }
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(Color.Yellow)
                            .border(1.dp, Color.White, CircleShape)
                    )
                }

                // Draw Blocks
                blocks.forEach { block ->
                    // Appearance animation
                    LaunchedEffect(block) {
                        block.scale.animateTo(1f, spring(dampingRatio = Spring.DampingRatioMediumBouncy))
                    }
                    
                    Box(
                        modifier = Modifier
                            .offset {
                                IntOffset(
                                    (block.x * maxWidth.toPx()).toInt() - 25.dp.toPx().toInt(),
                                    (block.y * maxHeight.toPx()).toInt()
                                )
                            }
                            .scale(block.scale.value)
                            .size(50.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(block.color)
                            .border(2.dp, Color.White.copy(alpha = 0.5f), RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = block.value.toString(),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                }

                // Rocket (Player)
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset {
                            IntOffset(
                                (rocketX * maxWidth.toPx()).toInt() - 30.dp.toPx().toInt(),
                                (0.92f * maxHeight.toPx()).toInt()
                            )
                        }
                        .rotate(rotation)
                        .size(60.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(Res.drawable.img_rocket),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                }
                
                // Game Over Overlay
                if (isGameOver) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.7f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                "GAME OVER",
                                color = Color.White,
                                fontSize = 40.sp,
                                fontWeight = FontWeight.Black
                            )
                            Spacer(Modifier.height(16.dp))
                            Button(
                                onClick = {
                                    blocks.clear()
                                    bullets.clear()
                                    score = 0
                                    isGameOver = false
                                    rocketTargetX = 0.5f
                                },
                                shape = RoundedCornerShape(20.dp)
                            ) {
                                Text("RESTART", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
            
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
fun ScoreChip(label: String, value: Int) {
    Surface(
        color = Color(0xFF1976D2).copy(alpha = 0.8f),
        shape = RoundedCornerShape(20.dp),
        shadowElevation = 4.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
        ) {
            Text(
                text = "$label: ",
                color = Color.White.copy(alpha = 0.7f),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            
            AnimatedContent(
                targetState = value,
                transitionSpec = {
                    (slideInVertically { height -> height } + fadeIn()).togetherWith(slideOutVertically { height -> -height } + fadeOut())
                },
                label = "ScoreAnim"
            ) { targetValue ->
                Text(
                    text = targetValue.toString(),
                    color = Color.Yellow,
                    fontWeight = FontWeight.Black,
                    fontSize = 18.sp
                )
            }
        }
    }
}
