package com.example.project1.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AppViewModel : ViewModel() {
    // Current User Profile
    var userProfile by mutableStateOf<UserProfile?>(null)

    // Preferences State (Backed by userProfile)
    var selectedCurrency by mutableStateOf("Indian Rupee")
    var priceAlertsEnabled by mutableStateOf(true)
    var volAlertsEnabled by mutableStateOf(false)
    var selectedRegion by mutableStateOf("India")
    var selectedLanguage by mutableStateOf("English (India)")
    var benchmarkMode by mutableStateOf("Auto (RBI CPI)")
    var manualInflationRate by mutableStateOf("6.5")

    // History State
    val historyItems = mutableStateListOf<HistoryItem>()
    
    // Holdings State
    val holdings = mutableStateListOf<UserHolding>()

    // Latest Calculation Result
    var lastCagrResult by mutableStateOf<CagrResultData?>(null)
    var lastXirrResult by mutableStateOf<XirrResultData?>(null)

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            val profile = FirebaseManager.getUserProfile()
            if (profile != null) {
                userProfile = profile
                selectedCurrency = profile.currency
                priceAlertsEnabled = profile.priceAlerts
                volAlertsEnabled = profile.volAlerts
                selectedRegion = profile.region
                selectedLanguage = profile.language
            } else {
                userProfile = null
            }
            loadHistory()
            loadHoldings()
        }
    }

    private fun loadHistory() {
        viewModelScope.launch {
            val items = FirebaseManager.getHistory()
            historyItems.clear()
            historyItems.addAll(items)
        }
    }

    private fun loadHoldings() {
        viewModelScope.launch {
            val items = FirebaseManager.getHoldings()
            holdings.clear()
            holdings.addAll(items)
        }
    }

    fun syncPreferences() {
        val currentProfile = userProfile ?: UserProfile()
        val updatedProfile = currentProfile.copy(
            currency = selectedCurrency,
            priceAlerts = priceAlertsEnabled,
            volAlerts = volAlertsEnabled,
            region = selectedRegion,
            language = selectedLanguage
        )
        viewModelScope.launch {
            FirebaseManager.saveUserProfile(updatedProfile)
            userProfile = updatedProfile
        }
    }

    fun deleteHistoryItem(item: HistoryItem) {
        viewModelScope.launch {
            FirebaseManager.deleteHistoryItem(item.id)
            historyItems.remove(item)
        }
    }

    fun addHistoryItem(title: String, duration: String, realValue: String, nominalValue: String, type: String) {
        val date = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault()).format(java.util.Date())
        val newItem = HistoryItem(
            title = title,
            date = date,
            duration = duration,
            realValue = realValue,
            nominalValue = nominalValue,
            type = type
        )
        viewModelScope.launch {
            FirebaseManager.saveHistoryItem(newItem)
            loadHistory() // Reload to get the ID from Firebase
        }
    }
}

data class CagrResultData(
    val nominalCagr: String,
    val realCagr: String,
    val finalNominal: String,
    val finalReal: String,
    val name: String
)

data class XirrResultData(
    val nominalXirr: String,
    val realXirr: String,
    val netGain: String
)
