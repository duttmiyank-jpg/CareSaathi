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
fun FamilyScreen(
    viewModel: CareSaathiViewModel,
    onBack: () -> Unit
) {
    val familyMembers by viewModel.familyMembers.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Family & Caretaker Ecosystem", fontWeight = FontWeight.Bold) },
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
            Text(
                text = "Authorized Ecosystem Members (${familyMembers.size})",
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
                items(familyMembers) { fam ->
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
                                        color = SoftMint,
                                        modifier = Modifier.size(44.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = SecondaryTeal)
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = fam.name,
                                            style = MaterialTheme.typography.titleLarge.copy(
                                                color = PrimaryNavy,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 16.sp
                                            ),
                                            maxLines = 1
                                        )
                                        Text(
                                            text = fam.relation,
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
                                    color = SoftMint,
                                    shape = MaterialTheme.shapes.small
                                ) {
                                    Text(
                                        text = fam.role,
                                        maxLines = 1,
                                        softWrap = false,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = SecondaryTeal,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        ),
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))
                            Divider(color = AppBackground, thickness = 1.dp)
                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Phone: ${fam.phone}",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = PrimaryNavy,
                                        fontSize = 12.sp
                                    ),
                                    maxLines = 1
                                )
                                Text(
                                    text = "Code: ${fam.inviteCode}",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = SecondaryTeal,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
