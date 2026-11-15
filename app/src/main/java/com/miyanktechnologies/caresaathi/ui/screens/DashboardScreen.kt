package com.miyanktechnologies.caresaathi.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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

@Composable
fun DashboardScreen(
    viewModel: CareSaathiViewModel,
    onNavigateMedication: () -> Unit,
    onNavigateSchedule: () -> Unit,
    onNavigateAppointments: () -> Unit,
    onNavigateFamily: () -> Unit,
    onNavigateRefill: () -> Unit,
    onNavigateAssistant: () -> Unit,
    onNavigateEmergency: () -> Unit,
    onNavigateNotifications: () -> Unit,
    onNavigateSettings: () -> Unit
) {
    val currentUser by viewModel.currentUser.collectAsState()
    val scheduleDoses by viewModel.scheduleDoses.collectAsState()
    val medications by viewModel.medications.collectAsState()
    val appointments by viewModel.appointments.collectAsState()
    val notifications by viewModel.notifications.collectAsState()
    val isHindi by viewModel.isHindi.collectAsState()

    val unreadNotifs = notifications.count { !it.read }
    val completedCount = scheduleDoses.count { it.status == DoseStatus.TAKEN }
    val totalDoses = scheduleDoses.size
    val adherencePercent = if (totalDoses > 0) (completedCount * 100) / totalDoses else 100

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            Surface(
                color = PrimaryNavy,
                shadowElevation = 4.dp
            ) {
                Row(
                    modifier = Modifier
                        .statusBarsPadding()
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = MaterialTheme.shapes.small,
                            color = SecondaryTeal,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (isHindi) "नमस्ते, ${currentUser.name}" else "Hello, ${currentUser.name}",
                                style = MaterialTheme.typography.titleLarge.copy(
                                   color = Color.White,
                                   fontWeight = FontWeight.Bold,
                                   fontSize = 16.sp
                                ),
                                maxLines = 1
                            )
                            Text(
                                text = "Role: ${currentUser.role.name}",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = SoftMint,
                                    fontSize = 12.sp
                                ),
                                maxLines = 1
                            )
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // AI Assistant shortcut
                        IconButton(onClick = onNavigateAssistant) {
                            Icon(
                                imageVector = Icons.Default.Chat,
                                contentDescription = "AI Assistant",
                                tint = SoftMint
                            )
                        }

                        // Notifications
                        Box {
                            IconButton(onClick = onNavigateNotifications) {
                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = "Notifications",
                                    tint = Color.White
                                )
                            }
                            if (unreadNotifs > 0) {
                                Surface(
                                    color = DangerRed,
                                    shape = MaterialTheme.shapes.small,
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .size(18.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = "$unreadNotifs",
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                color = Color.White,
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                    }
                                }
                            }
                        }

                        IconButton(onClick = onNavigateSettings) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Settings",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateEmergency,
                containerColor = DangerRed,
                contentColor = Color.White
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Warning, contentDescription = "SOS")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "EMERGENCY SOS",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBackground)
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            // Adherence & Today Overview Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = MaterialTheme.shapes.large
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (isHindi) "आज की दवाइयां" else "Today's Medication Overview",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    color = PrimaryNavy,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                ),
                                maxLines = 2
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "$completedCount of $totalDoses doses completed today",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = SecondaryText,
                                    fontSize = 13.sp
                                ),
                                maxLines = 1
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            color = SoftMint,
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text(
                                text = "$adherencePercent% Adherence",
                                maxLines = 1,
                                softWrap = false,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    color = SecondaryTeal,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                ),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    LinearProgressIndicator(
                        progress = { completedCount.toFloat() / if (totalDoses > 0) totalDoses.toFloat() else 1f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp),
                        color = SecondaryTeal,
                        trackColor = SoftMint,
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Quick Actions Grid
            Text(
                text = if (isHindi) "त्वरित कार्य" else "Quick Actions",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = PrimaryNavy,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                QuickActionCard(
                    title = "Schedule",
                    icon = Icons.Default.DateRange,
                    color = SecondaryTeal,
                    modifier = Modifier.weight(1f),
                    onClick = onNavigateSchedule
                )
                QuickActionCard(
                    title = "Medicines",
                    icon = Icons.Default.MedicalServices,
                    color = PrimaryNavy,
                    modifier = Modifier.weight(1f),
                    onClick = onNavigateMedication
                )
                QuickActionCard(
                    title = "Appointments",
                    icon = Icons.Default.Event,
                    color = WarningYellow,
                    modifier = Modifier.weight(1f),
                    onClick = onNavigateAppointments
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                QuickActionCard(
                    title = "Family Access",
                    icon = Icons.Default.Group,
                    color = PrimaryNavy,
                    modifier = Modifier.weight(1f),
                    onClick = onNavigateFamily
                )
                QuickActionCard(
                    title = "Refill Tracker",
                    icon = Icons.Default.LocalPharmacy,
                    color = SecondaryTeal,
                    modifier = Modifier.weight(1f),
                    onClick = onNavigateRefill
                )
                QuickActionCard(
                    title = "AI Assistant",
                    icon = Icons.Default.Chat,
                    color = WarningYellow,
                    modifier = Modifier.weight(1f),
                    onClick = onNavigateAssistant
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Due Now / Upcoming Doses Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isHindi) "आगामी खुराक" else "Upcoming Doses",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = PrimaryNavy,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                )
                TextButton(onClick = onNavigateSchedule) {
                    Text(text = "View All", color = SecondaryTeal, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            scheduleDoses.filter { it.status != DoseStatus.TAKEN }.take(3).forEach { dose ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
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
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = MaterialTheme.shapes.small,
                                color = if (dose.status == DoseStatus.DUE_NOW) WarningYellow.copy(alpha = 0.2f) else SoftMint,
                                modifier = Modifier.size(44.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Alarm,
                                        contentDescription = null,
                                        tint = if (dose.status == DoseStatus.DUE_NOW) WarningYellow else SecondaryTeal
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
                                        fontSize = 15.sp
                                    ),
                                    maxLines = 1
                                )
                                Text(
                                    text = "${dose.scheduledTime} • ${dose.timing} • ${dose.timeSlot}",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = SecondaryText,
                                        fontSize = 12.sp
                                    ),
                                    maxLines = 1
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = { viewModel.confirmDose(dose.id) },
                            colors = ButtonDefaults.buttonColors(containerColor = SecondaryTeal),
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
                            shape = MaterialTheme.shapes.small
                        ) {
                            Text(text = "Take", color = Color.White, fontWeight = FontWeight.Bold, maxLines = 1, softWrap = false)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Low Stock / Refill Alert Banner
            val lowStockMeds = medications.filter { it.remainingStock <= 10 }
            if (lowStockMeds.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = WarningYellow.copy(alpha = 0.15f)),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = WarningYellow,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Medicine Refill Warning",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    color = PrimaryNavy,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            )
                            Text(
                                text = "${lowStockMeds.first().name} has only ${lowStockMeds.first().remainingStock} tablets left.",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = PrimaryNavy,
                                    fontSize = 13.sp
                                )
                            )
                        }
                        TextButton(onClick = onNavigateRefill) {
                            Text(text = "Refill", color = PrimaryNavy, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(80.dp)) // Bottom padding for FAB
        }
    }
}

@Composable
fun QuickActionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(90.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                shape = MaterialTheme.shapes.small,
                color = color.copy(alpha = 0.15f),
                modifier = Modifier.size(36.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(18.dp))
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = PrimaryNavy,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 11.sp
                ),
                maxLines = 1,
                softWrap = false
            )
        }
    }
}
