package com.miyanktechnologies.caresaathi

import com.miyanktechnologies.caresaathi.data.model.*
import com.miyanktechnologies.caresaathi.data.repository.CareSaathiRepository
import org.junit.Test
import org.junit.Assert.*

class CareSaathiUnitTest {

    @Test
    fun testAdherenceCalculation() {
        val doses = listOf(
            ScheduleDose("1", "m1", "Amlodipine", "5mg", "Morning", "08:00 AM", "After Food", DoseStatus.TAKEN),
            ScheduleDose("2", "m2", "Metformin", "500mg", "Morning", "08:30 AM", "After Food", DoseStatus.TAKEN),
            ScheduleDose("3", "m4", "Calcium", "500mg", "Afternoon", "01:30 PM", "After Food", DoseStatus.UPCOMING),
            ScheduleDose("4", "m2", "Metformin", "500mg", "Night", "08:00 PM", "After Food", DoseStatus.UPCOMING)
        )

        val completedCount = doses.count { it.status == DoseStatus.TAKEN }
        val totalDoses = doses.size
        val adherence = (completedCount * 100) / totalDoses

        assertEquals(50, adherence)
    }

    @Test
    fun testRoleSwitching() {
        val repository = CareSaathiRepository()
        assertEquals(UserRole.ELDERLY, repository.currentUser.value.role)

        repository.switchUserRole(UserRole.FAMILY_MEMBER)
        assertEquals(UserRole.FAMILY_MEMBER, repository.currentUser.value.role)
        assertEquals("Aarav Sharma", repository.currentUser.value.name)

        repository.switchUserRole(UserRole.CARETAKER)
        assertEquals(UserRole.CARETAKER, repository.currentUser.value.role)
        assertEquals("Nurse Sunita Devi", repository.currentUser.value.name)
    }

    @Test
    fun testEmergencySOSGeneration() {
        val repository = CareSaathiRepository()
        val alert = repository.triggerEmergencySOS("Ramnath Sharma")

        assertNotNull(alert)
        assertEquals("Ramnath Sharma", alert.triggeredBy)
        assertEquals("Active", alert.status)
        assertTrue(repository.emergencyAlerts.value.isNotEmpty())
    }
}
