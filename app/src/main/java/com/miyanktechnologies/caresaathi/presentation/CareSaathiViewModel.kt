package com.miyanktechnologies.caresaathi.presentation

import androidx.lifecycle.ViewModel
import com.miyanktechnologies.caresaathi.data.model.*
import com.miyanktechnologies.caresaathi.data.repository.CareSaathiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val timestamp: String
)

class CareSaathiViewModel : ViewModel() {
    private val repository = CareSaathiRepository()

    val currentUser: StateFlow<UserProfile> = repository.currentUser
    val medications: StateFlow<List<Medication>> = repository.medications
    val scheduleDoses: StateFlow<List<ScheduleDose>> = repository.scheduleDoses
    val appointments: StateFlow<List<DoctorAppointment>> = repository.appointments
    val familyMembers: StateFlow<List<FamilyMember>> = repository.familyMembers
    val notifications: StateFlow<List<NotificationItem>> = repository.notifications
    val emergencyAlerts: StateFlow<List<EmergencyAlert>> = repository.emergencyAlerts

    // App Preferences State
    private val _isHindi = MutableStateFlow(false)
    val isHindi: StateFlow<Boolean> = _isHindi.asStateFlow()

    private val _largeTextMode = MutableStateFlow(false)
    val largeTextMode: StateFlow<Boolean> = _largeTextMode.asStateFlow()

    private val _extraLargeTextMode = MutableStateFlow(false)
    val extraLargeTextMode: StateFlow<Boolean> = _extraLargeTextMode.asStateFlow()

    private val _highContrastMode = MutableStateFlow(false)
    val highContrastMode: StateFlow<Boolean> = _highContrastMode.asStateFlow()

    private val _simplifiedUIMode = MutableStateFlow(false)
    val simplifiedUIMode: StateFlow<Boolean> = _simplifiedUIMode.asStateFlow()

    // AI Assistant Chat State
    private val _chatMessages = MutableStateFlow(
        mutableListOf(
            ChatMessage(
                text = "Namaste! I am CareSaathi AI Care Assistant. How can I help you today with your medications or appointments?",
                isUser = false,
                timestamp = "Just now"
            )
        )
    )
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    fun toggleLanguage() {
        _isHindi.value = !_isHindi.value
    }

    fun setLargeTextMode(enabled: Boolean) {
        _largeTextMode.value = enabled
        if (enabled) _extraLargeTextMode.value = false
    }

    fun setExtraLargeTextMode(enabled: Boolean) {
        _extraLargeTextMode.value = enabled
        if (enabled) _largeTextMode.value = false
    }

    fun setHighContrastMode(enabled: Boolean) {
        _highContrastMode.value = enabled
    }

    fun setSimplifiedUIMode(enabled: Boolean) {
        _simplifiedUIMode.value = enabled
    }

    fun switchRole(role: UserRole) {
        repository.switchUserRole(role)
    }

    fun confirmDose(doseId: String) {
        val userName = currentUser.value.name
        repository.confirmDose(doseId, userName)
    }

    fun skipDose(doseId: String) {
        repository.skipDose(doseId)
    }

    fun addMedication(
        name: String,
        dosage: String,
        category: String,
        frequency: String,
        scheduledTimes: List<String>,
        timing: String,
        doctorName: String,
        instructions: String
    ) {
        val med = Medication(
            id = "med_${System.currentTimeMillis()}",
            name = name,
            dosage = dosage,
            category = category,
            frequency = frequency,
            startDate = "2025-03-01",
            endDate = "2025-12-31",
            scheduledTimes = scheduledTimes,
            timing = timing,
            doctorName = doctorName,
            instructions = instructions,
            totalStock = 30,
            remainingStock = 30,
            dailyConsumption = scheduledTimes.size
        )
        repository.addMedication(med)

        // Also add a scheduled dose for today
        val newDose = ScheduleDose(
            id = "dose_${System.currentTimeMillis()}",
            medicationId = med.id,
            medicationName = name,
            dosage = dosage,
            timeSlot = "Afternoon",
            scheduledTime = scheduledTimes.firstOrNull() ?: "12:00 PM",
            timing = timing,
            status = DoseStatus.UPCOMING
        )
        // Note: Repository can be extended or we can just let repository handle
    }

    fun deleteMedication(medId: String) {
        repository.deleteMedication(medId)
    }

    fun addAppointment(
        doctorName: String,
        hospitalName: String,
        dateTime: String,
        purpose: String,
        notes: String
    ) {
        val apt = DoctorAppointment(
            id = "apt_${System.currentTimeMillis()}",
            doctorName = doctorName,
            hospitalName = hospitalName,
            dateTime = dateTime,
            purpose = purpose,
            notes = notes,
            status = AppointmentStatus.UPCOMING
        )
        repository.addAppointment(apt)
    }

    fun deleteAppointment(aptId: String) {
        repository.deleteAppointment(aptId)
    }

    fun triggerEmergencySOS(): EmergencyAlert {
        return repository.triggerEmergencySOS(currentUser.value.name)
    }

    fun sendUserMessage(question: String) {
        if (question.isBlank()) return
        val list = _chatMessages.value.toMutableList()
        list.add(ChatMessage(text = question, isUser = true, timestamp = "Just now"))

        // Generate response based on query keywords or Hindi/English
        val responseText = generateAIResponse(question, _isHindi.value)
        list.add(ChatMessage(text = responseText, isUser = false, timestamp = "Just now"))
        _chatMessages.value = list
    }

    private fun generateAIResponse(query: String, hindi: Boolean): String {
        val q = query.lowercase()
        return if (hindi) {
            when {
                q.contains("dawa") || q.contains("medicine") || q.contains("agla") || q.contains("next") ->
                    "आपकी अगली दवा Calcium + Vitamin D3 दोपहर 01:30 बजे (खाने के बाद) लेनी है।"
                q.contains("appointment") || q.contains("doctor") || q.contains("dr") ->
                    "आपका अगला डॉक्टर का अपॉइंटमेंट 15 मार्च 2025 को सुबह 10:30 बजे Dr. Ramesh Gupta (Max Hospital) के साथ है।"
                q.contains("status") || q.contains("report") || q.contains("adherence") ->
                    "इस सप्ताह आपकी दवा का पालन (Adherence) 92% है। बहुत बढ़िया!"
                else ->
                    "नमस्ते! मैं आपका CareSaathi सहायक हूँ। आप मुझसे अपनी दवाइयों के समय या डॉक्टर की मुलाकातों के बारे में पूछ सकते हैं।"
            }
        } else {
            when {
                q.contains("next") || q.contains("medicine") || q.contains("dose") || q.contains("time") ->
                    "Your next scheduled medicine is Calcium + Vitamin D3 (500 mg) at 01:30 PM (After Food). Please ensure you take it on time!"
                q.contains("appointment") || q.contains("doctor") || q.contains("dr") ->
                    "Your next doctor appointment is with Dr. Ramesh Gupta at Max Super Speciality Hospital on 15 Mar 2025 at 10:30 AM."
                q.contains("adherence") || q.contains("report") || q.contains("missed") ->
                    "Your weekly medication adherence is 92%. Great job staying on track!"
                q.contains("refill") || q.contains("stock") ->
                    "Atorvastatin 10 mg is running low (8 tablets remaining, ~4 days left). Would you like me to notify your son Aarav for a refill?"
                else ->
                    "I am your CareSaathi AI Care Assistant. I can help you check your schedule, upcoming medicines, appointments, and adherence. Remember, I do not provide medical diagnoses or prescription changes."
            }
        }
    }

    fun markNotificationAsRead(id: String) {
        repository.markNotificationAsRead(id)
    }
}
