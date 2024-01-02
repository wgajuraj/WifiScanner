package com.example.wifiscanner.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WifiReadingDao {
    @Query("SELECT * FROM wifi_readings ORDER BY rssi ASC")
    fun getAll(): Flow<List<WifiReading>>

    @Query("SELECT DISTINCT ssid FROM wifi_readings ORDER BY ssid ASC")
    fun getAllSsids(): Flow<List<String>>

    @Query("SELECT * FROM wifi_readings WHERE ssid = :ssid ORDER BY date DESC")
    fun getBySsid(ssid: String): Flow<List<WifiReading>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(wifiReading: WifiReading)

    @Delete
    suspend fun delete(wifiReading: WifiReading)

    @Update
    suspend fun update(wifiReading: WifiReading)
}