package com.example.wifiscanner.data

import kotlinx.coroutines.flow.Flow

interface WifiReadingsRepository {
    fun wifiReadingStream(): Flow<List<WifiReading>>
    fun wifiUniqueSsidStream(): Flow<List<String>>
    fun wifiSsidStream(ssid: String): Flow<List<WifiReading>>
    suspend fun addWifiReading(wifiReading: WifiReading)
    suspend fun deleteWifiReading(wifiReading: WifiReading)
    suspend fun updateWifiReading(wifiReading: WifiReading)
}