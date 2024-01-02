package com.example.wifiscanner.data

import kotlinx.coroutines.flow.Flow

class RoomWifiReadingsRepository(private val wifiReadingDao: WifiReadingDao): WifiReadingsRepository {
    override fun wifiReadingStream(): Flow<List<WifiReading>> = wifiReadingDao.getAll()
    override fun wifiUniqueSsidStream(): Flow<List<String>> = wifiReadingDao.getAllSsids()
    override fun wifiSsidStream(ssid: String): Flow<List<WifiReading>> = wifiReadingDao.getBySsid(ssid)
    override suspend fun addWifiReading(wifiReading: WifiReading) = wifiReadingDao.insert(wifiReading)
    override suspend fun deleteWifiReading(wifiReading: WifiReading) = wifiReadingDao.delete(wifiReading)
    override suspend fun updateWifiReading(wifiReading: WifiReading) = wifiReadingDao.update(wifiReading)
}