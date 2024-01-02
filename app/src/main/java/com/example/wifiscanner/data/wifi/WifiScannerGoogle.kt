package com.example.wifiscanner.data.wifi

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.ScanResult
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WifiScannerGoogle(context: Context) {

    private val TAG = "WifiScannerGoogle"

    private val appContext = context.applicationContext

    private val wifiManager = appContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

    private val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val network = connectivityManager.activeNetwork

//    var scanResults: MutableState<List<ScanResult>?> = mutableStateOf(null)
    var scanResults: MutableStateFlow<List<ScanResult>?> = MutableStateFlow(null)
    var activeConnectionInfo: MutableState<WifiInfo?> = mutableStateOf(null)

    private val wifiScanReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)
            if (success) {
                scanSuccess()
            } else {
                scanFailure()
            }
        }
    }

    init {
        Log.d(TAG, "Scan Init")
        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        appContext.registerReceiver(wifiScanReceiver, intentFilter)
        CoroutineScope(Dispatchers.IO).launch {
            while (isActive) {
                startScan()
                getActiveConnectionInfo()
                delay(32000)
            }
        }
    }

    fun startScan() {
        Log.d(TAG, "Scan Started")
        wifiManager.startScan()
    }

    suspend fun getActiveConnectionInfo() {
        val wifiInfo = wifiManager.connectionInfo
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)

        if (network != null)
            if ((networkCapabilities != null) && networkCapabilities.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI
                )
            ) {
                withContext(Dispatchers.Main) {
                    activeConnectionInfo.value = wifiInfo
                    Log.d(TAG, "Scanner RSSI: ${wifiInfo.rssi}")
                }
            }
    }

    fun cleanup() {
        appContext.unregisterReceiver(wifiScanReceiver)
    }

    @SuppressLint("MissingPermission")
    private fun scanSuccess() {
        Log.d(TAG, "Scan Success")
        val results = wifiManager.scanResults
        scanResults.tryEmit(results)

    }

    @SuppressLint("MissingPermission")
    private fun scanFailure() {
        Log.d(TAG, "Scan Failed")
        // handle failure: new scan did NOT succeed
        // consider using old scan results: these are the OLD results!
        val results = wifiManager.scanResults
        scanResults.tryEmit(results)
    }
}