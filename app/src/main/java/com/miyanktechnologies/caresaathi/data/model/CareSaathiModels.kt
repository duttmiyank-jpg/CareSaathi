package com.miyanktechnologies.caresaathi.data.model

data class UserProfile(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val role: UserRole,
    val age: Int,
    val bloodGroup: String,
    val emergencyContact: String,
    val profileImage: String = ""
)

enum class UserRole {
    ELDERLY, FAMILY_MEMBER, CARETAKER
}

data class Medication(
    val id: String,
    val name: String,
    val dosage: String,
    val category: String, // e.g., "Blood Pressure", "Diabetes", "Cardio", "Vitamins"
    val frequency: String, // e.g., "Daily", "Twice Daily", "Weekly"
    val startDate: String,
    val endDate: String,
    val scheduledTimes: List<String>, // e.g. ["08:00 AM", "08:00 PM"]
    val timing: String, // "Before Food" or "After Food"
    val doctorName: String,
    val instructions: String,
    val active: Boolean = true,
    val totalStock: Int = 30,
    val remainingStock: Int = 22,
    val dailyConsumption: Int = 2
)

data class ScheduleDose(
    val id: String,
    val medicationId: String,
    val medicationName: String,
    val dosage: String,
    val timeSlot: String, // "Morning", "Afternoon", "Evening", "Night"
    val scheduledTime: String,
    val timing: String,
    val status: DoseStatus, // UPCOMING, DUE_NOW, TAKEN, MISSED, SKIPPED
    val confirmedBy: String? = null,
    val confirmationTime: String? = null
)

enum class DoseStatus {
    UPCOMING, DUE_NOW, TAKEN, MISSED, SKIPPED
}

data class DoctorAppointment(
    val id: String,
    val doctorName: String,
    val hospitalName: String,
    val dateTime: String,
    val purpose: String,
    val notes: String,
    val status: AppointmentStatus // UPCOMING, COMPLETED, CANCELLED
)

enum class AppointmentStatus {
    UPCOMING, COMPLETED, CANCELLED
}

data class FamilyMember(
    val id: String,
    val name: String,
    val relation: String,
    val phone: String,
    val email: String,
    val role: String, // "Administrator", "View-Only", "Caregiver"
    val inviteCode: String = ""
)

data class NotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val time: String,
    val category: NotificationCategory,
    val read: Boolean = false
)

enum class NotificationCategory {
    MEDICATION, MISSED, APPOINTMENT, REFILL, FAMILY, CARETAKER, EMERGENCY
}

data class EmergencyAlert(
    val id: String,
    val timestamp: String,
    val triggeredBy: String,
    val status: String, // "Active", "Acknowledged", "Resolved"
    val notes: String
)
