package com.example.wifiscanner.data

import android.content.Context
import com.example.wifiscanner.data.wifi.WifiScannerGoogle

class AppDataContainer(private val context: Context) : AppContainer {
    override val wifiReadingsRepository: WifiReadingsRepository by lazy {
        RoomWifiReadingsRepository(AppDatabase.getDatabase(context).wifiReadingDao())
    }

    override val wifiScanner: WifiScannerGoogle by lazy {
        WifiScannerGoogle(context)
    }

}
