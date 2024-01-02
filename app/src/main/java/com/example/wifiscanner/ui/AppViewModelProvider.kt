package com.example.wifiscanner.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.wifiscanner.WifiScannerApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            WifiScannerViewModel(
                wifiScannerApplication().container.wifiReadingsRepository,
                wifiScannerApplication().container.wifiScanner
            )
        }
    }
}

fun CreationExtras.wifiScannerApplication(): WifiScannerApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as WifiScannerApplication)