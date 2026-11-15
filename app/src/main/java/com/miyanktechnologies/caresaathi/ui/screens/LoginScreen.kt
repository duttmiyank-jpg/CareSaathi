package com.miyanktechnologies.caresaathi.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miyanktechnologies.caresaathi.data.model.UserRole
import com.miyanktechnologies.caresaathi.presentation.CareSaathiViewModel
import com.miyanktechnologies.caresaathi.ui.theme.PrimaryNavy
import com.miyanktechnologies.caresaathi.ui.theme.SecondaryTeal
import com.miyanktechnologies.caresaathi.ui.theme.SoftMint

@Composable
fun LoginScreen(
    viewModel: CareSaathiViewModel,
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("ramnath.sharma@caresaathi.com") }
    var password by remember { mutableStateOf("••••••••") }
    val scrollState = rememberScrollState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Surface(
                shape = MaterialTheme.shapes.extraLarge,
                color = SoftMint,
                modifier = Modifier.size(80.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = SecondaryTeal,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Welcome to CareSaathi",
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = PrimaryNavy,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Care Connected. Lives Protected.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Sign In",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = PrimaryNavy,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email or Phone") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = MaterialTheme.shapes.medium
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = MaterialTheme.shapes.medium
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = onLoginSuccess,
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryNavy),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(
                            text = "Login",
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Hackathon Judge Demo Mode Quick Logins
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = SoftMint),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "🌟 Hackathon Demo Mode Quick Login",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = PrimaryNavy,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Select a demo profile to instantly explore role-specific dashboards:",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = PrimaryNavy.copy(alpha = 0.8f),
                            fontSize = 13.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            viewModel.switchRole(UserRole.ELDERLY)
                            onLoginSuccess()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SecondaryTeal),
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text("👴 Elderly User (Ramnath Sharma / Dada Ji)")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            viewModel.switchRole(UserRole.FAMILY_MEMBER)
                            onLoginSuccess()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryNavy),
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text("👨‍👩‍👦 Family Member (Aarav Sharma / Son)")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            viewModel.switchRole(UserRole.CARETAKER)
                            onLoginSuccess()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryNavy),
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text("👩‍⚕️ Caretaker (Nurse Sunita Devi)")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
