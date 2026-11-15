package com.miyanktechnologies.caresaathi.data.repository

import com.miyanktechnologies.caresaathi.data.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CareSaathiRepository {

    private val _currentUser = MutableStateFlow(
        UserProfile(
            id = "user_001",
            name = "Ramnath Sharma (Dada Ji)",
            email = "ramnath.sharma@caresaathi.com",
            phone = "+91 98765 43210",
            role = UserRole.ELDERLY,
            age = 72,
            bloodGroup = "B+",
            emergencyContact = "+91 98765 11111 (Aarav Sharma - Son)"
        )
    )
    val currentUser: StateFlow<UserProfile> = _currentUser.asStateFlow()

    private val _medications = MutableStateFlow(
        mutableListOf(
            Medication(
                id = "med_1",
                name = "Amlodipine",
                dosage = "5 mg",
                category = "Blood Pressure",
                frequency = "Daily",
                startDate = "2025-01-01",
                endDate = "2025-12-31",
                scheduledTimes = listOf("08:00 AM"),
                timing = "After Food",
                doctorName = "Dr. Ramesh Gupta",
                instructions = "Take with a glass of water. Do not skip.",
                totalStock = 30,
                remainingStock = 14,
                dailyConsumption = 1
            ),
            Medication(
                id = "med_2",
                name = "Metformin",
                dosage = "500 mg",
                category = "Diabetes",
                frequency = "Twice Daily",
                startDate = "2025-01-01",
                endDate = "2025-12-31",
                scheduledTimes = listOf("08:30 AM", "08:00 PM"),
                timing = "After Food",
                doctorName = "Dr. Ananya Roy",
                instructions = "Take immediately after meals to avoid stomach upset.",
                totalStock = 60,
                remainingStock = 28,
                dailyConsumption = 2
            ),
            Medication(
                id = "med_3",
                name = "Atorvastatin",
                dosage = "10 mg",
                category = "Cardio",
                frequency = "Daily",
                startDate = "2025-01-01",
                endDate = "2025-12-31",
                scheduledTimes = listOf("09:00 PM"),
                timing = "Before Sleep",
                doctorName = "Dr. Ramesh Gupta",
                instructions = "Bedtime dose for cholesterol management.",
                totalStock = 30,
                remainingStock = 8, // Low stock warning!
                dailyConsumption = 1
            ),
            Medication(
                id = "med_4",
                name = "Calcium + Vitamin D3",
                dosage = "500 mg",
                category = "Vitamins",
                frequency = "Daily",
                startDate = "2025-02-01",
                endDate = "2025-08-01",
                scheduledTimes = listOf("01:30 PM"),
                timing = "After Food",
                doctorName = "Dr. Ananya Roy",
                instructions = "Bone health support.",
                totalStock = 30,
                remainingStock = 20,
                dailyConsumption = 1
            )
        )
    )
    val medications: StateFlow<List<Medication>> = _medications.asStateFlow()

    private val _scheduleDoses = MutableStateFlow(
        mutableListOf(
            ScheduleDose(
                id = "dose_1",
                medicationId = "med_1",
                medicationName = "Amlodipine",
                dosage = "5 mg",
                timeSlot = "Morning",
                scheduledTime = "08:00 AM",
                timing = "After Food",
                status = DoseStatus.TAKEN,
                confirmedBy = "Ramnath Sharma",
                confirmationTime = "08:15 AM"
            ),
            ScheduleDose(
                id = "dose_2",
                medicationId = "med_2",
                medicationName = "Metformin",
                dosage = "500 mg",
                timeSlot = "Morning",
                scheduledTime = "08:30 AM",
                timing = "After Food",
                status = DoseStatus.TAKEN,
                confirmedBy = "Ramnath Sharma",
                confirmationTime = "08:32 AM"
            ),
            ScheduleDose(
                id = "dose_3",
                medicationId = "med_4",
                medicationName = "Calcium + Vitamin D3",
                dosage = "500 mg",
                timeSlot = "Afternoon",
                scheduledTime = "01:30 PM",
                timing = "After Food",
                status = DoseStatus.DUE_NOW,
                confirmedBy = null,
                confirmationTime = null
            ),
            ScheduleDose(
                id = "dose_4",
                medicationId = "med_2",
                medicationName = "Metformin",
                dosage = "500 mg",
                timeSlot = "Night",
                scheduledTime = "08:00 PM",
                timing = "After Food",
                status = DoseStatus.UPCOMING,
                confirmedBy = null,
                confirmationTime = null
            ),
            ScheduleDose(
                id = "dose_5",
                medicationId = "med_3",
                medicationName = "Atorvastatin",
                dosage = "10 mg",
                timeSlot = "Night",
                scheduledTime = "09:00 PM",
                timing = "Before Sleep",
                status = DoseStatus.UPCOMING,
                confirmedBy = null,
                confirmationTime = null
            )
        )
    )
    val scheduleDoses: StateFlow<List<ScheduleDose>> = _scheduleDoses.asStateFlow()

    private val _appointments = MutableStateFlow(
        mutableListOf(
            DoctorAppointment(
                id = "apt_1",
                doctorName = "Dr. Ramesh Gupta",
                hospitalName = "Max Super Speciality Hospital",
                dateTime = "15 Mar 2025, 10:30 AM",
                purpose = "Quarterly Cardiac & BP Evaluation",
                notes = "Bring blood pressure diary and fasting lipid profile reports.",
                status = AppointmentStatus.UPCOMING
            ),
            DoctorAppointment(
                id = "apt_2",
                doctorName = "Dr. Ananya Roy",
                hospitalName = "Apollo Clinic & Diabetes Centre",
                    dateTime = "22 Mar 2025, 04:00 PM",
                purpose = "HbA1c Follow-up Check",
                notes = "Fasting blood sugar test required 1 day prior.",
                status = AppointmentStatus.UPCOMING
            )
        )
    )
    val appointments: StateFlow<List<DoctorAppointment>> = _appointments.asStateFlow()

    private val _familyMembers = MutableStateFlow(
        mutableListOf(
            FamilyMember(
                id = "fam_1",
                name = "Aarav Sharma",
                relation = "Son (Primary Caregiver)",
                phone = "+91 98765 11111",
                email = "aarav.sharma@gmail.com",
                role = "Administrator",
                inviteCode = "CS-9821-AZ"
            ),
            FamilyMember(
                id = "fam_2",
                name = "Priya Sharma",
                relation = "Daughter-in-law",
                phone = "+91 98765 22222",
                email = "priya.sharma@gmail.com",
                role = "View & Confirm",
                inviteCode = "CS-4412-PY"
            ),
            FamilyMember(
                id = "fam_3",
                name = "Nurse Sunita Devi",
                relation = "Assigned Caretaker",
                phone = "+91 98765 33333",
                email = "sunita.caretaker@caresaathi.com",
                role = "Caregiver",
                inviteCode = "CS-7788-SN"
            )
        )
    )
    val familyMembers: StateFlow<List<FamilyMember>> = _familyMembers.asStateFlow()

    private val _notifications = MutableStateFlow(
        mutableListOf(
            NotificationItem(
                id = "notif_1",
                title = "Medication Due",
                message = "Calcium + Vitamin D3 (500 mg) is due at 01:30 PM.",
                time = "10 mins ago",
                category = NotificationCategory.MEDICATION,
                read = false
            ),
            NotificationItem(
                id = "notif_2",
                title = "Low Stock Refill Alert",
                message = "Atorvastatin 10 mg has only 8 tablets remaining (4 days left). Tap to order refill.",
                time = "2 hours ago",
                category = NotificationCategory.REFILL,
                read = false
            ),
            NotificationItem(
                id = "notif_3",
                title = "Doctor Appointment Reminder",
                message = "Upcoming checkup with Dr. Ramesh Gupta on 15 Mar 2025 at 10:30 AM.",
                time = "1 day ago",
                category = NotificationCategory.APPOINTMENT,
                read = true
            )
        )
    )
    val notifications: StateFlow<List<NotificationItem>> = _notifications.asStateFlow()

    private val _emergencyAlerts = MutableStateFlow(
        mutableListOf<EmergencyAlert>()
    )
    val emergencyAlerts: StateFlow<List<EmergencyAlert>> = _emergencyAlerts.asStateFlow()

    // Methods
    fun switchUserRole(role: UserRole) {
        val updatedUser = when (role) {
            UserRole.ELDERLY -> UserProfile(
                id = "user_001",
                name = "Ramnath Sharma (Dada Ji)",
                email = "ramnath.sharma@caresaathi.com",
                phone = "+91 98765 43210",
                role = UserRole.ELDERLY,
                age = 72,
                bloodGroup = "B+",
                emergencyContact = "+91 98765 11111 (Aarav Sharma - Son)"
            )
            UserRole.FAMILY_MEMBER -> UserProfile(
                id = "user_002",
                name = "Aarav Sharma",
                email = "aarav.sharma@gmail.com",
                phone = "+91 98765 11111",
                role = UserRole.FAMILY_MEMBER,
                age = 42,
                bloodGroup = "O+",
                emergencyContact = "Dr. Ramesh Gupta"
            )
            UserRole.CARETAKER -> UserProfile(
                id = "user_003",
                name = "Nurse Sunita Devi",
                email = "sunita.caretaker@caresaathi.com",
                phone = "+91 98765 33333",
                role = UserRole.CARETAKER,
                age = 35,
                bloodGroup = "A+",
                emergencyContact = "Hospital Emergency: 108"
            )
        }
        _currentUser.value = updatedUser
    }

    fun confirmDose(doseId: String, confirmedBy: String) {
        val list = _scheduleDoses.value.toMutableList()
        val index = list.indexOfFirst { it.id == doseId }
        if (index != -1) {
            val dose = list[index]
            val timeStr = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
            list[index] = dose.copy(
                status = DoseStatus.TAKEN,
                confirmedBy = confirmedBy,
                confirmationTime = timeStr
            )
            _scheduleDoses.value = list
        }
    }

    fun skipDose(doseId: String) {
        val list = _scheduleDoses.value.toMutableList()
        val index = list.indexOfFirst { it.id == doseId }
        if (index != -1) {
            val dose = list[index]
            list[index] = dose.copy(status = DoseStatus.SKIPPED)
            _scheduleDoses.value = list
        }
    }

    fun addMedication(med: Medication) {
        val list = _medications.value.toMutableList()
        list.add(med)
        _medications.value = list
    }

    fun deleteMedication(medId: String) {
        val list = _medications.value.toMutableList()
        list.removeAll { it.id == medId }
        _medications.value = list
    }

    fun addAppointment(apt: DoctorAppointment) {
        val list = _appointments.value.toMutableList()
        list.add(apt)
        _appointments.value = list
    }

    fun deleteAppointment(aptId: String) {
        val list = _appointments.value.toMutableList()
        list.removeAll { it.id == aptId }
        _appointments.value = list
    }

    fun triggerEmergencySOS(triggeredBy: String): EmergencyAlert {
        val timeStr = SimpleDateFormat("dd MMM yyyy, hh:mm:ss a", Locale.getDefault()).format(Date())
        val alert = EmergencyAlert(
            id = "alert_${System.currentTimeMillis()}",
            timestamp = timeStr,
            triggeredBy = triggeredBy,
            status = "Active",
            notes = "Emergency SOS dispatched to family members and primary caretaker."
        )
        val list = _emergencyAlerts.value.toMutableList()
        list.add(0, alert)
        _emergencyAlerts.value = list

        // Also add notification
        val notifList = _notifications.value.toMutableList()
        notifList.add(
            0,
            NotificationItem(
                id = "notif_sos_${System.currentTimeMillis()}",
                title = "🚨 EMERGENCY SOS ALERT",
                message = "SOS triggered by $triggeredBy at $timeStr. Caretaker & family notified.",
                time = "Just now",
                category = NotificationCategory.EMERGENCY,
                read = false
            )
        )
        _notifications.value = notifList

        return alert
    }

    fun markNotificationAsRead(notifId: String) {
        val list = _notifications.value.toMutableList()
        val index = list.indexOfFirst { it.id == notifId }
        if (index != -1) {
            list[index] = list[index].copy(read = true)
            _notifications.value = list
        }
    }
}
