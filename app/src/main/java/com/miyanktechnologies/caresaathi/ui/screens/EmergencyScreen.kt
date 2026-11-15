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
fun EmergencyScreen(
    viewModel: CareSaathiViewModel,
    onBack: () -> Unit
) {
    val emergencyAlerts by viewModel.emergencyAlerts.collectAsState()
    val familyMembers by viewModel.familyMembers.collectAsState()
    var alertTriggered by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Emergency SOS Assistance", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DangerRed,
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Giant SOS Button
            Button(
                onClick = {
                    viewModel.triggerEmergencySOS()
                    alertTriggered = true
                },
                colors = ButtonDefaults.buttonColors(containerColor = DangerRed),
                modifier = Modifier
                    .size(180.dp),
                shape = MaterialTheme.shapes.extraLarge,
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(imageVector = Icons.Default.Warning, contentDescription = null, modifier = Modifier.size(64.dp), tint = Color.White)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "SOS",
                        style = MaterialTheme.typography.displayLarge.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 36.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Tap to instantly alert all family members and assigned caretakers.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = SecondaryText,
                    fontSize = 14.sp
                ),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            if (alertTriggered) {
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = SoftMint),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = SecondaryTeal)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "🚨 SOS Alert successfully broadcasted to Family & Caretaker ecosystem!",
                            color = PrimaryNavy,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Emergency Contacts",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = PrimaryNavy,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(familyMembers) { fam ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = CardBackground),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = MaterialTheme.shapes.small,
                                    color = SoftMint,
                                    modifier = Modifier.size(40.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(imageVector = Icons.Default.Phone, contentDescription = null, tint = SecondaryTeal)
                                    }
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = fam.name,
                                        style = MaterialTheme.typography.titleLarge.copy(
                                            color = PrimaryNavy,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp
                                        )
                                    )
                                    Text(
                                        text = "${fam.relation} • ${fam.phone}",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = SecondaryText,
                                            fontSize = 13.sp
                                        )
                                    )
                                }
                            }

                            Button(
                                onClick = { /* Call action simulation */ },
                                colors = ButtonDefaults.buttonColors(containerColor = SecondaryTeal),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text("Call", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}
