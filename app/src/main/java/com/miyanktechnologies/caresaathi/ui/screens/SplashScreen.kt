package com.miyanktechnologies.caresaathi.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miyanktechnologies.caresaathi.ui.theme.PrimaryNavy
import com.miyanktechnologies.caresaathi.ui.theme.SecondaryTeal
import com.miyanktechnologies.caresaathi.ui.theme.SoftMint
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigateNext: () -> Unit) {
    var startAnimation by remember { mutableStateOf(false) }
    val scaleAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1.0f else 0.7f,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
        label = "scale"
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(2200)
        onNavigateNext()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryNavy),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .scale(scaleAnim.value)
                .padding(24.dp)
        ) {
            Surface(
                shape = MaterialTheme.shapes.extraLarge,
                color = SecondaryTeal.copy(alpha = 0.2f),
                modifier = Modifier.size(110.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "CareSaathi Logo",
                        tint = SecondaryTeal,
                        modifier = Modifier.size(64.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "CareSaathi",
                style = MaterialTheme.typography.displayLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Care Connected. Lives Protected.",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = SoftMint,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            )

            Spacer(modifier = Modifier.height(48.dp))

            CircularProgressIndicator(
                color = SecondaryTeal,
                strokeWidth = 3.dp,
                modifier = Modifier.size(36.dp)
            )
        }
    }
}
