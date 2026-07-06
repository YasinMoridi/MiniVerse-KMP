package com.yasinmoridi.miniverse.presentation.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.yasinmoridi.miniverse.utils.AppColor
import com.yasinmoridi.miniverse.utils.UIStrings

@Composable
fun SettingsUI(navController: NavHostController) {
    var volume by remember { mutableStateOf(0.4f) }
    var resolution by remember { mutableStateOf(1f) }
    var maxFps by remember { mutableStateOf(1f) }
    var vibrationEnabled by remember { mutableStateOf(true) }
    var soundEnabled by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColor.SETTINGS_BG)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = UIStrings.BACK,
                        tint = AppColor.DARK_NAVY,
                        modifier = Modifier.size(32.dp)
                    )
                }
                
                Text(
                    text = UIStrings.SETTINGS,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black,
                    color = AppColor.DARK_NAVY
                )
                
                // Placeholder to balance the row
                Spacer(modifier = Modifier.size(48.dp))
            }

            // Settings Card
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                color = AppColor.SETTINGS_CARD_BG,
                shape = RoundedCornerShape(32.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    SettingSlider(
                        label = UIStrings.VOLUME,
                        value = volume,
                        onValueChange = { volume = it }
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    SettingSlider(
                        label = UIStrings.RESOLUTION,
                        value = resolution,
                        onValueChange = { resolution = it }
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    SettingSlider(
                        label = UIStrings.MAX_FPS,
                        value = maxFps,
                        onValueChange = { maxFps = it }
                    )
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    // FPS Preview Box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .background(AppColor.PREVIEW_BOX_BG),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "60\nFPS",
                            modifier = Modifier.align(Alignment.TopStart).padding(16.dp),
                            fontWeight = FontWeight.Black,
                            fontSize = 24.sp,
                            color = AppColor.DARK_NAVY,
                            lineHeight = 24.sp
                        )
                        
                        // Circle in the middle
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(Color.Gray)
                        )
                    }
                    
                    Spacer(modifier = Modifier.weight(1f))
                    
                    // Switches
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = UIStrings.VIBRATION,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            color = AppColor.DARK_NAVY
                        )
                        
                        CustomSwitch(
                            checked = vibrationEnabled,
                            onCheckedChange = { vibrationEnabled = it }
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = UIStrings.SOUND,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            color = AppColor.DARK_NAVY
                        )

                        CustomSwitch(
                            checked = soundEnabled,
                            onCheckedChange = { soundEnabled = it }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = UIStrings.LANGUAGE,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            color = AppColor.DARK_NAVY
                        )

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            TextButton(onClick = { UIStrings.language = com.yasinmoridi.miniverse.utils.AppLanguage.ENGLISH }) {
                                Text(
                                    text = UIStrings.ENGLISH,
                                    fontWeight = if (UIStrings.language == com.yasinmoridi.miniverse.utils.AppLanguage.ENGLISH) FontWeight.Black else FontWeight.Normal,
                                    color = if (UIStrings.language == com.yasinmoridi.miniverse.utils.AppLanguage.ENGLISH) AppColor.DARK_NAVY else Color.Gray
                                )
                            }
                            Text("/", color = Color.Gray)
                            TextButton(onClick = { UIStrings.language = com.yasinmoridi.miniverse.utils.AppLanguage.PERSIAN }) {
                                Text(
                                    text = UIStrings.PERSIAN,
                                    fontWeight = if (UIStrings.language == com.yasinmoridi.miniverse.utils.AppLanguage.PERSIAN) FontWeight.Black else FontWeight.Normal,
                                    color = if (UIStrings.language == com.yasinmoridi.miniverse.utils.AppLanguage.PERSIAN) AppColor.DARK_NAVY else Color.Gray
                                )
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    // Footer
                    Text(
                        text = UIStrings.TERMS_AND_PRIVACY,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = AppColor.DARK_NAVY
                    )
                }
            }
        }
    }
}

@Composable
fun SettingSlider(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 20.sp,
            fontWeight = FontWeight.Black,
            color = AppColor.DARK_NAVY
        )
        
        Slider(
            value = value,
            onValueChange = onValueChange,
            colors = SliderDefaults.colors(
                thumbColor = AppColor.SLIDER_THUMB,
                activeTrackColor = AppColor.SLIDER_TRACK_ACTIVE,
                inactiveTrackColor = AppColor.SLIDER_TRACK_INACTIVE
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun CustomSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = SwitchDefaults.colors(
            checkedThumbColor = AppColor.SWITCH_THUMB_ON,
            checkedTrackColor = AppColor.DARK_NAVY,
            uncheckedThumbColor = Color.White,
            uncheckedTrackColor = AppColor.DARK_NAVY
        )
    )
}
