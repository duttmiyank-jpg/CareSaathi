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
import com.miyanktechnologies.caresaathi.data.model.DoseStatus
import com.miyanktechnologies.caresaathi.presentation.CareSaathiViewModel
import com.miyanktechnologies.caresaathi.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    viewModel: CareSaathiViewModel,
    onBack: () -> Unit
) {
    val scheduleDoses by viewModel.scheduleDoses.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Today's Schedule", "Morning", "Afternoon", "Evening", "Night")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Medication Schedule", fontWeight = FontWeight.Bold) },
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
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                containerColor = CardBackground,
                contentColor = SecondaryTeal,
                edgePadding = 0.dp
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title, fontWeight = FontWeight.Bold) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val filteredDoses = when (selectedTab) {
                1 -> scheduleDoses.filter { it.timeSlot.equals("Morning", true) }
                2 -> scheduleDoses.filter { it.timeSlot.equals("Afternoon", true) }
                3 -> scheduleDoses.filter { it.timeSlot.equals("Evening", true) }
                4 -> scheduleDoses.filter { it.timeSlot.equals("Night", true) }
                else -> scheduleDoses
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredDoses) { dose ->
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
                                Row(
                                    modifier = Modifier.weight(1f),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        shape = MaterialTheme.shapes.small,
                                        color = when (dose.status) {
                                            DoseStatus.TAKEN -> SoftMint
                                            DoseStatus.DUE_NOW -> WarningYellow.copy(alpha = 0.2f)
                                            else -> AppBackground
                                        },
                                        modifier = Modifier.size(44.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(
                                                imageVector = when (dose.status) {
                                                    DoseStatus.TAKEN -> Icons.Default.CheckCircle
                                                    DoseStatus.DUE_NOW -> Icons.Default.Alarm
                                                    else -> Icons.Default.Schedule
                                                },
                                                contentDescription = null,
                                                tint = when (dose.status) {
                                                    DoseStatus.TAKEN -> SecondaryTeal
                                                    DoseStatus.DUE_NOW -> WarningYellow
                                                    else -> SecondaryText
                                                }
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "${dose.medicationName} (${dose.dosage})",
                                            style = MaterialTheme.typography.titleLarge.copy(
                                                color = PrimaryNavy,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 16.sp
                                            ),
                                            maxLines = 1
                                        )
                                        Text(
                                            text = "${dose.scheduledTime} • ${dose.timing} • ${dose.timeSlot}",
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                color = SecondaryText,
                                                fontSize = 13.sp
                                            ),
                                            maxLines = 1
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                Surface(
                                    color = when (dose.status) {
                                        DoseStatus.TAKEN -> SoftMint
                                        DoseStatus.DUE_NOW -> WarningYellow.copy(alpha = 0.2f)
                                        else -> AppBackground
                                    },
                                    shape = MaterialTheme.shapes.small
                                ) {
                                    Text(
                                        text = dose.status.name,
                                        maxLines = 1,
                                        softWrap = false,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = when (dose.status) {
                                                DoseStatus.TAKEN -> SecondaryTeal
                                                DoseStatus.DUE_NOW -> WarningYellow
                                                else -> SecondaryText
                                            },
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        ),
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                    )
                                }
                            }

                            if (dose.status != DoseStatus.TAKEN) {
                                Spacer(modifier = Modifier.height(16.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Button(
                                        onClick = { viewModel.confirmDose(dose.id) },
                                        colors = ButtonDefaults.buttonColors(containerColor = SecondaryTeal),
                                        modifier = Modifier.weight(1f),
                                        shape = MaterialTheme.shapes.small
                                    ) {
                                        Icon(imageVector = Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Confirm Taken", fontSize = 13.sp)
                                    }

                                    OutlinedButton(
                                        onClick = { viewModel.skipDose(dose.id) },
                                        modifier = Modifier.weight(1f),
                                        shape = MaterialTheme.shapes.small
                                    ) {
                                        Text("Skip", fontSize = 13.sp, color = SecondaryText)
                                    }
                                }
                            } else {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Confirmed by ${dose.confirmedBy ?: "User"} at ${dose.confirmationTime ?: ""}",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = SecondaryTeal,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
