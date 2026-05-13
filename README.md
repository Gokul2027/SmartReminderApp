# 📍 Smart Location-Based Reminder App

> **Conference Paper** | ICAEM 2026 — ISBN: - 978-81-69206-65-5 
> 📄 [View Base Paper](https://drive.google.com/file/d/1YTfYcr-zcCuzMnsxh7O5KFKMI1ATIaBv/view?usp=drive_link)

A native Android application that triggers reminders based on your physical location. Using GPS and geofencing, the app notifies users when they enter or leave a saved place — backed by lightweight AI for task categorization, habit tracking, and smart place recommendations.

---

## 📑 Table of Contents

- [Overview](#overview)
- [Conference Details](#conference-details)
- [Features](#features)
- [System Architecture](#system-architecture)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Permissions Required](#permissions-required)
- [How It Works](#how-it-works)
- [AI Components](#ai-components)
- [Testing Strategy](#testing-strategy)
- [Error Handling](#error-handling)
- [Future Enhancements](#future-enhancements)
- [License](#license)

---

## 🔍 Overview

Most reminder apps rely on time — you set a time, you get a notification. **SmartReminderApp** changes the paradigm: reminders fire based on *where you are*. Forgot to pick up medicine? Set a reminder for when you enter the pharmacy. Need to call someone when you leave the office? The geofence handles it.

The app also learns from your usage patterns through a lightweight habit tracker, making suggestions smarter over time — all without sending any data to a cloud server.

---

## 🎓 Conference Details

| Field      | Details                                                                           |
|------------|-----------------------------------------------------------------------------------|
| Paper ID   | ICAEM26012174                                                                     |
| Conference | ICAEM 2026 (International Conference on Advanced Engineering & Management)        |
| Domain     | Mobile Computing / Context-Aware Systems                                          |
| Base Paper | [📄 Open PDF](https://drive.google.com/file/d/1YTfYcr-zcCuzMnsxh7O5KFKMI1ATIaBv/view?usp=drive_link) |
| Language   | Kotlin (100%)                                                                     |
| Platform   | Android (API 34)                                                                  |

---

## ✨ Features

### Core
- 📝 **Task CRUD** — Create, read, update, and delete reminders with persistent local storage (Room DB)
- 📍 **Location-Triggered Reminders** — Notifications fire on geofence `ENTER` or `EXIT` events
- 🗺️ **Map Picker** — Interactive map (OpenStreetMap) with tap-to-set location and Nominatim text search
- 🔔 **Local Notifications** — Rich notifications with a snooze action
- 🔁 **Boot Recovery** — Geofences restored automatically after device reboot or app update via WorkManager

### Smart / AI
- 🤖 **Task Categorizer** — Suggests a category (Shopping, Health, Work, etc.) from the task title using keyword mapping
- 📊 **Habit Tracker** — Learns from completed reminders to surface recurring usage patterns
- 🏪 **Place Recommendations** — Suggests nearby relevant places via Overpass API, ranked by distance and habit data

### Maps & Location (Free OSM Stack)
- **osmdroid** with OpenStreetMap tiles — no Google Maps SDK needed
- **Nominatim** for geocoding and address search
- **Overpass API** for nearby place lookups
- **Google Play Services Location** for fused GPS and geofencing

---

## 🏗️ System Architecture

The app follows **MVVM (Model-View-ViewModel)** with a clean separation of concerns:

```
┌─────────────────────────────────────────────────┐
│                   UI Layer                       │
│   Activities · XML Layouts · Adapters            │
│               ViewModels                         │
└───────────────────┬─────────────────────────────┘
                    │
┌───────────────────▼─────────────────────────────┐
│            Domain / Business Layer               │
│   TaskRepository · GeofenceManager · AI Modules  │
└──────┬────────────────────┬─────────────────────┘
       │                    │
┌──────▼──────┐   ┌─────────▼───────────────────┐
│  Data Layer │   │      System Services          │
│   Room DB   │   │  GPS · Notifications          │
│  DAO/Entity │   │  WorkManager · BroadcastRx    │
└─────────────┘   └─────────────────────────────┘
```

---

## 🛠️ Tech Stack

| Category         | Technology                                       |
|------------------|--------------------------------------------------|
| Language         | Kotlin 1.9.22                                    |
| Architecture     | MVVM + Repository Pattern                        |
| Database         | Room (SQLite)                                    |
| Location         | Google Play Services Location (Geofencing + FLP) |
| Maps             | osmdroid + OpenStreetMap                         |
| Geocoding        | Nominatim API                                    |
| Nearby Places    | Overpass API                                     |
| Background Tasks | WorkManager                                      |
| Notifications    | Android NotificationManager                      |
| Build System     | Gradle 8.2.2 + Kotlin KAPT                       |
| Min SDK          | Android 8.0 (API 26)                             |
| Target SDK       | Android 14 (API 34)                              |

---

## 📁 Project Structure

```
SmartReminderApp/
├── app/
│   └── src/main/
│       ├── java/com/.../smartreminder/
│       │   ├── ui/                     # Activities, Adapters, XML bindings
│       │   │   └── viewmodel/          # UI state & orchestration
│       │   ├── data/                   # Room Entity, DAO, Repository
│       │   ├── location/               # GeofenceManager, Boot recovery,
│       │   │                           #   BroadcastReceiver
│       │   ├── network/                # Nominatim search, Overpass nearby places
│       │   ├── ai/                     # TaskCategorizer, HabitTracker,
│       │   │                           #   PlaceRecommendationEngine
│       │   ├── notifications/          # Notification channel, Snooze flow
│       │   └── worker/                 # GeofenceSyncWorker (WorkManager)
│       └── res/
│           └── layout/                 # XML UI layouts
├── gradle/wrapper/
├── build.gradle                        # Project-level Gradle config
├── settings.gradle
├── gradle.properties
└── README.md
```

---

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog (2023.1.1) or newer
- Android SDK with **API 34** installed
- A **real Android device** — geofencing is unreliable on emulators
- Google Play Services on the device

### Setup Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/Gokul2027/SmartReminderApp.git
   cd SmartReminderApp
   ```

2. **Open in Android Studio**
   ```
   File → Open → select the project root folder
   ```

3. **Sync Gradle**
   - Allow Android Studio to download all dependencies automatically
   - If prompted to regenerate Gradle wrapper files, allow it

4. **No API key needed**
   - This project uses the free OSM stack (osmdroid, Nominatim, Overpass)
   - No Google Maps or Places API key is required

5. **Build and run**
   - Connect a real Android device via USB with Developer Mode enabled
   - Select the device in Android Studio and click ▶ **Run**

---

## 🔑 Permissions Required

| Permission                           | Purpose                                          |
|--------------------------------------|--------------------------------------------------|
| `ACCESS_FINE_LOCATION`               | Precise GPS for geofence placement               |
| `ACCESS_BACKGROUND_LOCATION`         | Trigger reminders when the app is in background  |
| `POST_NOTIFICATIONS` *(Android 13+)* | Display reminder notifications                   |
| `RECEIVE_BOOT_COMPLETED`             | Re-register geofences after device reboot        |

> ⚠️ On Android 11+, background location must be explicitly granted from **Settings → Apps → SmartReminder → Permissions → Location → Allow all the time**.

---

## 🔄 How It Works

```
User creates a task
        │
        ▼
TaskCategorizer suggests a category (AI)
        │
        ▼
User picks a location on MapPickerActivity (OSM + Nominatim)
        │
        ▼
TaskViewModel writes to Room DB
        │
        ▼
GeofenceManager registers geofence with Play Services
        │
   ┌────┴──────────────────────┐
   │  User enters / exits      │
   │  the geofence area        │
   └────┬──────────────────────┘
        │
        ▼
GeofenceBroadcastReceiver fires
        │
        ▼
NotificationHelper shows notification (+ Snooze action)
        │
        ▼
User marks task complete → HabitTracker records pattern
```

After a device reboot, **GeofenceSyncWorker** (WorkManager) automatically re-registers all active geofences so no reminders are lost.

---

## 🤖 AI Components

All AI runs **fully on-device** — no cloud API calls, no user data sent externally.

| Module                       | What It Does                                                             |
|------------------------------|--------------------------------------------------------------------------|
| `TaskCategorizer`            | Maps keywords in the task title to categories (Shopping, Health, Work…)  |
| `HabitTracker`               | Records task completion patterns to detect recurring habits              |
| `PlaceRecommendationEngine`  | Ranks Overpass results by distance combined with habit frequency weight   |

---

## 🧪 Testing Strategy

### Manual Testing Checklist

- [ ] Add a reminder (`ENTER` trigger), walk into the geofence → notification fires
- [ ] Edit the same task → geofence updates correctly (no duplicate)
- [ ] Mark a task complete → disappears from active list, habit recorded
- [ ] Reboot device → reminders still trigger
- [ ] Deny location permission → rationale dialog appears with Settings redirect
- [ ] Disable GPS → home screen shows a location-disabled banner
- [ ] Go offline → map search and nearby places handle it gracefully

### Unit Tests

| Test Class                       | Coverage                                        |
|----------------------------------|-------------------------------------------------|
| `TaskCategorizerTest`            | Keyword-to-category mapping logic               |
| `PlaceRecommendationEngineTest`  | Distance vs. habit-weight ranking algorithm     |

---

## 🛡️ Error Handling

- Empty task name or missing radius → inline validation error shown
- Location permission denied → rationale dialog with Settings redirect
- GPS disabled → persistent warning banner on the home screen
- No internet → graceful fallback for Nominatim search and Overpass calls
- Duplicate notifications suppressed via a per-task cooldown window

---

## 🔮 Future Enhancements

- ⏰ Time-window preferences per reminder (e.g., only trigger between 9 AM – 6 PM)
- 💾 Migrate settings and habit data to Jetpack DataStore
- ✅ Complete task directly from the notification action button
- 🧪 Instrumentation tests for geofence registration and Room DB flows
- 🗺️ Optional Google Maps / Places integration for venue ratings and richer metadata

---

## 📄 License

This project was developed as part of an academic conference submission (ICAEM 2026). It is open for reference and educational purposes.

---

<div align="center">
  <sub> &nbsp;·&nbsp; Presented at ICAEM 2026</sub>
</div>
