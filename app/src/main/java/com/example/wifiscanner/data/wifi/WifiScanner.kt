package com.example.wifiscanner.data.wifi

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.concurrent.Executor


//class WifiScanner(private val context: Context) {
//
//    val wifiManager: WifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
//
//    fun getWifiNetworks(): List<ScanResult>? {
//        return if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            wifiManager.scanResults
//        } else {
//            null
//        }
//    }
//
//    fun requestPermissions() {
//        ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0)
//    }
//}




//class WifiScanner(private val context: Context) {
//
//    val wifiManager: WifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
//
//    var scanResults: MutableStateFlow<List<ScanResult>?> = MutableStateFlow(null)
//
//    private val wifiScanReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context?, intent: Intent?) {
//            if (intent?.action == WifiManager.SCAN_RESULTS_AVAILABLE_ACTION) {
//                scanResults.tryEmit(getWifiNetworks())
//            }
//        }
//    }
//
//    init {
//        val intentFilter = IntentFilter().apply {
//            addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
//        }
//        context.registerReceiver(wifiScanReceiver, intentFilter)
//
//        CoroutineScope(Dispatchers.IO).launch {
//            wifiManager.startScan()
//            while (isActive) {
//                delay(5000)
//                scanResults.value = getWifiNetworks()
//            }
//        }
//    }
//
//    fun unregisterReceiver() {
//        context.unregisterReceiver(wifiScanReceiver)
//    }
//
//    fun getWifiNetworks(): List<ScanResult>? {
//        return if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            wifiManager.scanResults
//        } else {
//            null
//        }
//    }
//}



@RequiresApi(Build.VERSION_CODES.R)
class WifiScanner(private val context: Context) {

    val wifiManager: WifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

    var scanResults: MutableStateFlow<List<ScanResult>?> = MutableStateFlow(null)

    private val executor: Executor = ContextCompat.getMainExecutor(context)

    private val callback = @RequiresApi(Build.VERSION_CODES.R)
    object : WifiManager.ScanResultsCallback() {

        @SuppressLint("MissingPermission")
        override fun onScanResultsAvailable() {
            scanResults.tryEmit(wifiManager.scanResults)
        }
    }

    init {
        wifiManager.registerScanResultsCallback(executor, callback)

        CoroutineScope(Dispatchers.IO).launch {
            while (isActive) {
                wifiManager.startScan()
                delay(5000)
            }
        }
    }

    fun unregisterReceiver() {
        wifiManager.unregisterScanResultsCallback(callback)
    }
}

