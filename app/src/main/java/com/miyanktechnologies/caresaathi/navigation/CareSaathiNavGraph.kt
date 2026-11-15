package com.miyanktechnologies.caresaathi.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.miyanktechnologies.caresaathi.presentation.CareSaathiViewModel
import com.miyanktechnologies.caresaathi.ui.screens.*

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object Dashboard : Screen("dashboard")
    object Medication : Screen("medication")
    object Schedule : Screen("schedule")
    object Appointments : Screen("appointments")
    object Family : Screen("family")
    object Refill : Screen("refill")
    object Assistant : Screen("assistant")
    object Emergency : Screen("emergency")
    object Analytics : Screen("analytics")
    object Notifications : Screen("notifications")
    object Settings : Screen("settings")
}

@Composable
fun CareSaathiNavGraph(viewModel: CareSaathiViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateNext = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onGetStarted = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Login.route) {
            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                viewModel = viewModel,
                onNavigateMedication = { navController.navigate(Screen.Medication.route) },
                onNavigateSchedule = { navController.navigate(Screen.Schedule.route) },
                onNavigateAppointments = { navController.navigate(Screen.Appointments.route) },
                onNavigateFamily = { navController.navigate(Screen.Family.route) },
                onNavigateRefill = { navController.navigate(Screen.Refill.route) },
                onNavigateAssistant = { navController.navigate(Screen.Assistant.route) },
                onNavigateEmergency = { navController.navigate(Screen.Emergency.route) },
                onNavigateNotifications = { navController.navigate(Screen.Notifications.route) },
                onNavigateSettings = { navController.navigate(Screen.Settings.route) }
            )
        }
        composable(Screen.Medication.route) {
            MedicationScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
        composable(Screen.Schedule.route) {
            ScheduleScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
        composable(Screen.Appointments.route) {
            AppointmentsScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
        composable(Screen.Family.route) {
            FamilyScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
        composable(Screen.Refill.route) {
            RefillScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
        composable(Screen.Assistant.route) {
            AssistantScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
        composable(Screen.Emergency.route) {
            EmergencyScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
        composable(Screen.Analytics.route) {
            AnalyticsScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
        composable(Screen.Notifications.route) {
            NotificationsScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
        composable(Screen.Settings.route) {
            SettingsScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Dashboard.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
