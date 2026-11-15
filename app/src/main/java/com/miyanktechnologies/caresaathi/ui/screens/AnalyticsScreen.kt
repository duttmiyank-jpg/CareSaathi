package com.miyanktechnologies.caresaathi.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miyanktechnologies.caresaathi.presentation.CareSaathiViewModel
import com.miyanktechnologies.caresaathi.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsScreen(
    viewModel: CareSaathiViewModel,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Analytics & Adherence Reports", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryNavy,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBackground)
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Weekly Adherence Rate",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = PrimaryNavy,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "92%",
                                style = MaterialTheme.typography.displayLarge.copy(
                                    color = SecondaryTeal,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 42.sp
                                )
                            )
                            Text(
                                text = "28 of 30 doses taken on time",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = SecondaryText
                                )
                            )
                        }

                        Surface(
                            shape = MaterialTheme.shapes.medium,
                            color = SoftMint,
                            modifier = Modifier.size(72.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(imageVector = Icons.Default.TrendingUp, contentDescription = null, tint = SecondaryTeal, modifier = Modifier.size(36.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    LinearProgressIndicator(
                        progress = { 0.92f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(12.dp),
                        color = SecondaryTeal,
                        trackColor = SoftMint
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Monthly Health Summary",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = PrimaryNavy,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "• Completed Doses: 124", color = PrimaryNavy, fontSize = 15.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "• Missed Doses: 4 (Promptly resolved)", color = PrimaryNavy, fontSize = 15.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "• Completed Doctor Visits: 2", color = PrimaryNavy, fontSize = 15.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "• Refills Ordered: 3", color = PrimaryNavy, fontSize = 15.sp)
                }
            }
        }
    }
}
