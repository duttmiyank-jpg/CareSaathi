package com.miyanktechnologies.caresaathi.ui.screens

import androidx.compose.foundation.background
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
import com.miyanktechnologies.caresaathi.presentation.CareSaathiViewModel
import com.miyanktechnologies.caresaathi.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: CareSaathiViewModel,
    onBack: () -> Unit,
    onLogout: () -> Unit
) {
    val currentUser by viewModel.currentUser.collectAsState()
    val isHindi by viewModel.isHindi.collectAsState()
    val largeText by viewModel.largeTextMode.collectAsState()
    val extraLargeText by viewModel.extraLargeTextMode.collectAsState()
    val highContrast by viewModel.highContrastMode.collectAsState()
    val simplifiedUI by viewModel.simplifiedUIMode.collectAsState()

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings & Accessibility", fontWeight = FontWeight.Bold) },
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
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Profile Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = MaterialTheme.shapes.medium,
                        color = SecondaryTeal,
                        modifier = Modifier.size(56.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(32.dp))
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = currentUser.name,
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = PrimaryNavy,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        )
                        Text(
                            text = "${currentUser.email} • ${currentUser.phone}",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = SecondaryText,
                                fontSize = 13.sp
                            )
                        )
                        Text(
                            text = "Role: ${currentUser.role.name}",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = SecondaryTeal,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }

            // Accessibility Controls
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Elderly Accessibility Options",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = PrimaryNavy,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Large Text Mode", color = PrimaryNavy, fontWeight = FontWeight.Medium)
                        Switch(
                            checked = largeText,
                            onCheckedChange = { viewModel.setLargeTextMode(it) },
                            colors = SwitchDefaults.colors(checkedThumbColor = SecondaryTeal)
                        )
                    }

                    Divider(color = AppBackground, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Extra Large Text Mode", color = PrimaryNavy, fontWeight = FontWeight.Medium)
                        Switch(
                            checked = extraLargeText,
                            onCheckedChange = { viewModel.setExtraLargeTextMode(it) },
                            colors = SwitchDefaults.colors(checkedThumbColor = SecondaryTeal)
                        )
                    }

                    Divider(color = AppBackground, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("High Contrast Mode", color = PrimaryNavy, fontWeight = FontWeight.Medium)
                        Switch(
                            checked = highContrast,
                            onCheckedChange = { viewModel.setHighContrastMode(it) },
                            colors = SwitchDefaults.colors(checkedThumbColor = SecondaryTeal)
                        )
                    }
                }
            }

            // Language & Localization
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Language / भाषा",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = PrimaryNavy,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(if (isHindi) "हिंदी (Hindi)" else "English", color = PrimaryNavy, fontWeight = FontWeight.Medium)
                        Switch(
                            checked = isHindi,
                            onCheckedChange = { viewModel.toggleLanguage() },
                            colors = SwitchDefaults.colors(checkedThumbColor = SecondaryTeal)
                        )
                    }
                }
            }

            // Logout Button
            Button(
                onClick = onLogout,
                colors = ButtonDefaults.buttonColors(containerColor = DangerRed),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(imageVector = Icons.Default.Logout, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Logout",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}
