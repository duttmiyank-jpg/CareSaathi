package com.miyanktechnologies.caresaathi.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun RefillScreen(
    viewModel: CareSaathiViewModel,
    onBack: () -> Unit
) {
    val medications by viewModel.medications.collectAsState()
    var orderedMsg by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Medicine Refill Tracker", fontWeight = FontWeight.Bold) },
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
                .padding(16.dp)
        ) {
            if (orderedMsg != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = SoftMint)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = SecondaryTeal)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(text = orderedMsg!!, color = PrimaryNavy, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            Text(
                text = "Inventory & Refill Status",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = PrimaryNavy,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(medications) { med ->
                    val daysLeft = if (med.dailyConsumption > 0) med.remainingStock / med.dailyConsumption else 30
                    val isLow = med.remainingStock <= 10

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = CardBackground),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        shape = MaterialTheme.shapes.small,
                                        color = if (isLow) WarningYellow.copy(alpha = 0.2f) else SoftMint,
                                        modifier = Modifier.size(44.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(
                                                imageVector = Icons.Default.LocalPharmacy,
                                                contentDescription = null,
                                                tint = if (isLow) WarningYellow else SecondaryTeal
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text(
                                            text = "${med.name} (${med.dosage})",
                                            style = MaterialTheme.typography.titleLarge.copy(
                                                color = PrimaryNavy,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 16.sp
                                            )
                                        )
                                        Text(
                                            text = "Remaining Stock: ${med.remainingStock} tablets (~$daysLeft days left)",
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                color = if (isLow) DangerRed else SecondaryText,
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Button(
                                onClick = {
                                    orderedMsg = "Refill request dispatched for ${med.name} to pharmacy & family caregiver!"
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = if (isLow) WarningYellow else SecondaryTeal),
                                modifier = Modifier.fillMaxWidth(),
                                shape = MaterialTheme.shapes.small
                            ) {
                                Text(
                                    text = if (isLow) "⚠️ Request Urgent Refill" else "Order Refill",
                                    color = PrimaryNavy,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
