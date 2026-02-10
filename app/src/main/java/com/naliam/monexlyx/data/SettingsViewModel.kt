package com.naliam.monexlyx.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val dataStore = SettingsDataStore(application)

    // 🌙 Dark Mode
    val darkMode: StateFlow<Boolean> =
        dataStore.darkModeFlow.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    // 🔔 Notifications
    val notificationsEnabled: StateFlow<Boolean> =
        dataStore.notificationsFlow.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = true
        )

    // =========================
    // Actions
    // =========================

    fun setDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            dataStore.setDarkMode(enabled)
        }
    }

    fun setNotifications(enabled: Boolean) {
        viewModelScope.launch {
            dataStore.setNotifications(enabled)
        }
    }
}
