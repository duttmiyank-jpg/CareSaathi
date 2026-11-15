package com.miyanktechnologies.caresaathi.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miyanktechnologies.caresaathi.ui.theme.PrimaryNavy
import com.miyanktechnologies.caresaathi.ui.theme.SecondaryTeal
import com.miyanktechnologies.caresaathi.ui.theme.SoftMint

@Composable
fun OnboardingScreen(onGetStarted: () -> Unit) {
    var step by remember { mutableStateOf(0) }

    val titles = listOf(
        "Smart Medication Management",
        "Connected Family & Caretakers",
        "Emergency SOS & AI Care Assistant"
    )

    val descriptions = listOf(
        "Never miss a dose. Timely reminders, morning to night schedules, and pill refill tracking designed especially for elders.",
        "Keep your loved ones in the loop. Family members and assigned caretakers receive real-time updates and missed dose alerts.",
        "Instantly trigger emergency SOS alerts or ask our bilingual AI Care Assistant about your schedule in English or Hindi."
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top branding
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = SecondaryTeal,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "CareSaathi",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = PrimaryNavy,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            // Center Illustration / Icon
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Surface(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = SoftMint,
                    modifier = Modifier.size(140.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        val icon = when (step) {
                            0 -> Icons.Default.Favorite
                            1 -> Icons.Default.Person
                            else -> Icons.Default.Notifications
                        }
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = SecondaryTeal,
                            modifier = Modifier.size(72.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(36.dp))

                Text(
                    text = titles[step],
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = PrimaryNavy,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = descriptions[step],
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }

            // Bottom Actions & Page Indicator
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(bottom = 24.dp)
                ) {
                    repeat(3) { index ->
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .size(if (index == step) 24.dp else 8.dp, 8.dp)
                                .background(
                                    color = if (index == step) SecondaryTeal else SecondaryTeal.copy(alpha = 0.3f),
                                    shape = MaterialTheme.shapes.small
                                )
                        )
                    }
                }

                Button(
                    onClick = {
                        if (step < 2) step++
                        else onGetStarted()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryNavy),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = if (step < 2) "Continue" else "Get Started / Demo Mode",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                if (step < 2) {
                    TextButton(onClick = onGetStarted) {
                        Text(
                            text = "Skip",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = SecondaryTeal,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.height(36.dp))
                }
            }
        }
    }
}
