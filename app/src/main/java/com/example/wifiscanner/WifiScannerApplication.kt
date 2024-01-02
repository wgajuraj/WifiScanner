package com.example.wifiscanner

import android.app.Application
import androidx.compose.ui.platform.LocalContext
import com.example.wifiscanner.data.AppContainer
import com.example.wifiscanner.data.AppDataContainer

class WifiScannerApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }

}