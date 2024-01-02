package com.example.wifiscanner

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.wifiscanner.ui.WifiScannerApp
import com.example.wifiscanner.ui.theme.WifiScannerTheme
import com.example.wifiscanner.utils.requestPermissions

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WifiScannerTheme {
                requestPermissions(this)
                WifiScannerApp()
            }
        }
    }
}