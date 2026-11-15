package com.miyanktechnologies.caresaathi.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.miyanktechnologies.caresaathi.data.model.Medication
import com.miyanktechnologies.caresaathi.presentation.CareSaathiViewModel
import com.miyanktechnologies.caresaathi.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationScreen(
    viewModel: CareSaathiViewModel,
    onBack: () -> Unit
) {
    val medications by viewModel.medications.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var medicationToDelete by remember { mutableStateOf<Medication?>(null) }
    var showSaveConfirmDialog by remember { mutableStateOf(false) }

    // Form fields for adding medication
    var name by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Blood Pressure") }
    var frequency by remember { mutableStateOf("Daily") }
    var timeSlot by remember { mutableStateOf("08:00 AM") }
    var timing by remember { mutableStateOf("After Food") }
    var doctorName by remember { mutableStateOf("Dr. Ramesh Gupta") }
    var instructions by remember { mutableStateOf("Take with water.") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Medication Management", fontWeight = FontWeight.Bold) },
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = SecondaryTeal,
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Medication")
            }
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
                text = "Active Prescriptions (${medications.size})",
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
                                            Icon(
                                                imageVector = Icons.Default.MedicalServices,
                                                contentDescription = null,
                                                tint = SecondaryTeal
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "${med.name} (${med.dosage})",
                                            style = MaterialTheme.typography.titleLarge.copy(
                                                color = PrimaryNavy,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 16.sp
                                            ),
                                            maxLines = 1
                                        )
                                        Text(
                                            text = "${med.category} • ${med.frequency} • ${med.timing}",
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                color = SecondaryText,
                                                fontSize = 13.sp
                                            ),
                                            maxLines = 1
                                        )
                                    }
                                }

                                IconButton(onClick = { medicationToDelete = med }) {
                                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = DangerRed)
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
                                    text = "Prescribed by: ${med.doctorName}",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = SecondaryText,
                                        fontSize = 12.sp
                                    ),
                                    maxLines = 1
                                )
                                Surface(
                                    color = if (med.remainingStock <= 10) DangerRed.copy(alpha = 0.15f) else SoftMint,
                                    shape = MaterialTheme.shapes.small
                                ) {
                                    Text(
                                        text = "Stock: ${med.remainingStock}/${med.totalStock}",
                                        maxLines = 1,
                                        softWrap = false,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = if (med.remainingStock <= 10) DangerRed else SecondaryTeal,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        ),
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Instructions: ${med.instructions}",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = PrimaryNavy,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }
                }
            }
        }

        // Add Medication Dialog with keyboard avoidance (.imePadding and scroll)
        if (showAddDialog) {
            AlertDialog(
                onDismissRequest = { showAddDialog = false },
                title = { Text("Add New Medication", fontWeight = FontWeight.Bold) },
                text = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                            .imePadding()
                            .padding(vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Medicine Name (e.g. Paracetamol)") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = dosage,
                            onValueChange = { dosage = it },
                            label = { Text("Dosage (e.g. 500 mg)") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = category,
                            onValueChange = { category = it },
                            label = { Text("Category (e.g. Pain Relief)") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = timeSlot,
                            onValueChange = { timeSlot = it },
                            label = { Text("Time (e.g. 08:00 AM)") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = doctorName,
                            onValueChange = { doctorName = it },
                            label = { Text("Doctor Name") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = instructions,
                            onValueChange = { instructions = it },
                            label = { Text("Instructions") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (name.isNotBlank() && dosage.isNotBlank()) {
                                showSaveConfirmDialog = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SecondaryTeal)
                    ) {
                        Text("Save Medication", color = Color.White)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showAddDialog = false }) {
                        Text("Cancel", color = PrimaryNavy)
                    }
                }
            )
        }

        // Save Confirmation Dialog
        if (showSaveConfirmDialog) {
            AlertDialog(
                onDismissRequest = { showSaveConfirmDialog = false },
                title = { Text("Confirm Save", fontWeight = FontWeight.Bold) },
                text = { Text("Are you sure you want to save '$name' to your medication schedule?") },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.addMedication(
                                name = name,
                                dosage = dosage,
                                category = category,
                                frequency = frequency,
                                scheduledTimes = listOf(timeSlot),
                                timing = timing,
                                doctorName = doctorName,
                                instructions = instructions
                            )
                            showSaveConfirmDialog = false
                            showAddDialog = false
                            name = ""
                            dosage = ""
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SecondaryTeal)
                    ) {
                        Text("Confirm & Save", color = Color.White)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showSaveConfirmDialog = false }) {
                        Text("Review", color = PrimaryNavy)
                    }
                }
            )
        }

        // Delete Confirmation Dialog
        if (medicationToDelete != null) {
            AlertDialog(
                onDismissRequest = { medicationToDelete = null },
                title = { Text("Delete Medication", fontWeight = FontWeight.Bold) },
                text = { Text("Are you sure you want to delete '${medicationToDelete?.name}'? This will remove it permanently from your records.") },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.deleteMedication(medicationToDelete!!.id)
                            medicationToDelete = null
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = DangerRed)
                    ) {
                        Text("Delete", color = Color.White)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { medicationToDelete = null }) {
                        Text("Cancel", color = PrimaryNavy)
                    }
                }
            )
        }
    }
}
