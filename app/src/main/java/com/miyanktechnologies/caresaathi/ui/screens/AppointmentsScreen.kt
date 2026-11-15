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
import com.miyanktechnologies.caresaathi.data.model.DoctorAppointment
import com.miyanktechnologies.caresaathi.presentation.CareSaathiViewModel
import com.miyanktechnologies.caresaathi.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentsScreen(
    viewModel: CareSaathiViewModel,
    onBack: () -> Unit
) {
    val appointments by viewModel.appointments.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var appointmentToDelete by remember { mutableStateOf<DoctorAppointment?>(null) }
    var showSaveConfirmDialog by remember { mutableStateOf(false) }

    var doctorName by remember { mutableStateOf("") }
    var hospitalName by remember { mutableStateOf("") }
    var dateTime by remember { mutableStateOf("15 Mar 2025, 10:30 AM") }
    var purpose by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Doctor Appointments", fontWeight = FontWeight.Bold) },
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
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Appointment")
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
                text = "Upcoming Consultations (${appointments.size})",
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
                items(appointments) { apt ->
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
                                        color = WarningYellow.copy(alpha = 0.2f),
                                        modifier = Modifier.size(44.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(
                                                imageVector = Icons.Default.Event,
                                                contentDescription = null,
                                                tint = WarningYellow
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = apt.doctorName,
                                            style = MaterialTheme.typography.titleLarge.copy(
                                                color = PrimaryNavy,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 16.sp
                                            ),
                                            maxLines = 1
                                        )
                                        Text(
                                            text = apt.hospitalName,
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                color = SecondaryText,
                                                fontSize = 13.sp
                                            ),
                                            maxLines = 1
                                        )
                                    }
                                }

                                IconButton(onClick = { appointmentToDelete = apt }) {
                                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = DangerRed)
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))
                            Divider(color = AppBackground, thickness = 1.dp)
                            Spacer(modifier = Modifier.height(8.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.Schedule, contentDescription = null, tint = SecondaryTeal, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = apt.dateTime,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = PrimaryNavy,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Purpose: ${apt.purpose}",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = PrimaryNavy,
                                    fontSize = 13.sp
                                )
                            )
                            if (apt.notes.isNotBlank()) {
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Notes: ${apt.notes}",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = SecondaryText,
                                        fontSize = 12.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        // Add Appointment Dialog with keyboard avoidance (.imePadding and scroll)
        if (showAddDialog) {
            AlertDialog(
                onDismissRequest = { showAddDialog = false },
                title = { Text("Add Doctor Appointment", fontWeight = FontWeight.Bold) },
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
                            value = doctorName,
                            onValueChange = { doctorName = it },
                            label = { Text("Doctor Name (e.g. Dr. Ramesh Gupta)") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = hospitalName,
                            onValueChange = { hospitalName = it },
                            label = { Text("Hospital / Clinic Name") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = dateTime,
                            onValueChange = { dateTime = it },
                            label = { Text("Date & Time") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = purpose,
                            onValueChange = { purpose = it },
                            label = { Text("Purpose of Visit") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = notes,
                            onValueChange = { notes = it },
                            label = { Text("Notes / Lab Reports required") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (doctorName.isNotBlank() && hospitalName.isNotBlank()) {
                                showSaveConfirmDialog = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SecondaryTeal)
                    ) {
                        Text("Save Appointment", color = Color.White)
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
                text = { Text("Are you sure you want to schedule this appointment with $doctorName?") },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.addAppointment(
                                doctorName = doctorName,
                                hospitalName = hospitalName,
                                dateTime = dateTime,
                                purpose = purpose,
                                notes = notes
                            )
                            showSaveConfirmDialog = false
                            showAddDialog = false
                            doctorName = ""
                            hospitalName = ""
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
        if (appointmentToDelete != null) {
            AlertDialog(
                onDismissRequest = { appointmentToDelete = null },
                title = { Text("Delete Appointment", fontWeight = FontWeight.Bold) },
                text = { Text("Are you sure you want to delete the appointment with ${appointmentToDelete?.doctorName}? This action cannot be undone.") },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.deleteAppointment(appointmentToDelete!!.id)
                            appointmentToDelete = null
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = DangerRed)
                    ) {
                        Text("Delete", color = Color.White)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { appointmentToDelete = null }) {
                        Text("Cancel", color = PrimaryNavy)
                    }
                }
            )
        }
    }
}
