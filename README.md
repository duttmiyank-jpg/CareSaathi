# CareSaathi 🏥🤖
### Smart Elderly Care & Medication Management Platform
**Tagline: Care Connected. Lives Protected.**

CareSaathi is a premium, cross-platform healthcare and medication management ecosystem designed to bridge the gap between elderly patients, family members, and medical caretakers. Built specifically for competitive hackathons, CareSaathi combines cutting-edge mobile architecture with a human-centric, highly accessible UI/UX.

---

## 🌟 Key Features & Modules

1. **Smart Medication Management & Schedule**:
   - Complete CRUD operations for active prescriptions (Medicine name, dosage, category, frequency, timing, doctor notes).
   - Daily & Weekly calendar organized into Morning, Afternoon, Evening, and Night slots.
   - Real-time dose confirmations (*Taken, Due Now, Upcoming, Skipped*) with timestamp and user identity tracking.
   - Preventing duplicate dose confirmations.

2. **Smart Reminder & Escalation Engine**:
   - Automated detection of upcoming medication and grace period missed-dose detection.
   - Instant notifications and escalation alerts for family members and caretakers.

3. **Family & Caretaker Ecosystem**:
   - Unique invitation codes and passcode-based role permissions (*Administrator, View-Only, Caregiver*).
   - Activity timeline tracking who added, edited, or confirmed records.

4. **AI Care Assistant (Gemini-Powered)**:
   - Bilingual AI assistant supporting English and Hindi queries (*"When is my next medicine?"*, *"Which medicines are pending?"*, *"Show schedule"*).
   - Strict medical disclaimer safeguards (does not diagnose or prescribe).

5. **Emergency Assistance (SOS)**:
   - Instant one-tap SOS broadcast alerting all family members and caretakers with emergency contact dialing.

6. **Medicine Refill Tracker**:
   - Real-time inventory calculation (remaining stock, daily consumption, estimated days left) with urgent low-stock warnings.

7. **Doctor Appointments**:
   - Manage consultations with hospital/clinic names, date & time, purpose, and pre-visit notes.

8. **Elderly Accessibility Suite**:
   - **Large Text** & **Extra Large Text** modes.
   - **High Contrast** mode.
   - Simplified high-legibility interface designed for elderly users with zero technical knowledge.

9. **Analytics & Reports**:
   - Weekly and monthly adherence percentage calculations and missing vs. completed dose trends.

10. **Hackathon Demo Mode**:
    - Pre-populated sample data and quick-login demo accounts for Elderly User, Family Member, and Caretaker so judges can explore the app instantly.

---

## 🏗️ Technology & Architecture

- **Kotlin Multiplatform (KMP)** & **Compose Multiplatform (CMP)**
- **Jetpack Compose / Material 3** UI
- **MVVM Architecture**
- **Kotlin Coroutines & StateFlow**
- **Repository Pattern**
- **Jetpack Navigation Compose**

---

## 🌟 Hackathon Demo Mode Quick Logins

Judges can explore the app instantly by selecting any profile upon launch:
- 👴 **Elderly User**: Ramnath Sharma (Dada Ji)
- 👨‍👩‍👦 **Family Member**: Aarav Sharma (Son / Administrator)
- 👩‍⚕️ **Caretaker**: Nurse Sunita Devi (Assigned Caregiver)

---

## 🚀 Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/duttmiyank-jpg/CareSaathi.git
   ```
2. Open the project in **Android Studio**.
3. Sync Gradle and run on an Android Emulator or iOS device.

---

## 📄 License
Licensed under the MIT License. Built with ❤️ for better healthcare and elder care.
