package com.example.wifiscanner.data

import android.content.Context
import com.example.wifiscanner.data.wifi.WifiScannerGoogle

interface AppContainer {
    val wifiReadingsRepository: WifiReadingsRepository
    val wifiScanner: WifiScannerGoogle
}
