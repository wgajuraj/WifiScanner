package com.example.wifiscanner.ui

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wifiscanner.data.WifiReading
import com.example.wifiscanner.data.WifiReadingsRepository
import com.example.wifiscanner.data.wifi.WifiScannerGoogle
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.entryOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class WifiScannerViewModel(
    private val wifiReadingsRepository: WifiReadingsRepository,
    val wifiScanner: WifiScannerGoogle
) : ViewModel() {
    private val blankReading = WifiReading(0, Date(), "", 0, 0, 0)
    private val _currentReadingStream = MutableStateFlow(blankReading)
    val currentReadingStream: StateFlow<WifiReading> = _currentReadingStream
    val readingsListStream: Flow<List<WifiReading>> = wifiReadingsRepository.wifiReadingStream()
    val ssidsListStream: Flow<List<String>> =
        wifiReadingsRepository.wifiUniqueSsidStream()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    var scanResults = wifiScanner.scanResults
    var activeConnectionInfo = wifiScanner.activeConnectionInfo

    fun resetCurrentReading() = _currentReadingStream.update { blankReading }
    fun updateCurrentReading(wifiReading: WifiReading) = _currentReadingStream.update { wifiReading }

    fun saveWifiReading() = viewModelScope.launch {
        if (_currentReadingStream.value.id > 0) {
            wifiReadingsRepository.updateWifiReading(_currentReadingStream.value)
        } else {
            wifiReadingsRepository.addWifiReading(_currentReadingStream.value)
        }
    }

    fun getWifiReadingsBySsid(ssid: String) = wifiReadingsRepository.wifiSsidStream(ssid)

    fun refresh(delay: Long, onRefresh: () -> Unit) = viewModelScope.launch {
        _isRefreshing.update { true }
        onRefresh()
        delay(delay)
        _isRefreshing.update { false }
    }
}