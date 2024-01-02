package com.example.wifiscanner.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.Date

@Entity(tableName = "wifi_readings")
@TypeConverters(Converters::class)
data class WifiReading(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "date") val date: Date = Date(),
    @ColumnInfo(name = "ssid") val ssid: String,
    @ColumnInfo(name = "rssi") val rssi: Int,
    @ColumnInfo(name = "frequency") val frequency: Int,
    @ColumnInfo(name = "distance") val distance: Int
)

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}