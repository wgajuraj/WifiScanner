package com.example.wifiscanner.data.wifi

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.NetworkInfo
import android.net.NetworkRequest
import android.net.wifi.ScanResult
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.flow.MutableStateFlow

data class ActiveWifiInfo(
    val ssid: String,
    val rssi: Int,
    val linkSpeed: Int,
    val frequency: Int
)

@RequiresApi(Build.VERSION_CODES.Q)
fun getActiveWifiInfo(context: Context): ActiveWifiInfo {
    val appContext = context.applicationContext
    val connectivityManager =
        appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetwork
    val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

    val transportInfo = networkCapabilities?.transportInfo

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.getConnectionInfo()
        return if (wifiInfo != null) {
            ActiveWifiInfo(
                ssid = wifiInfo.ssid,
                rssi = wifiInfo.rssi,
                linkSpeed = wifiInfo.linkSpeed,
                frequency = wifiInfo.frequency
            )
        } else {
            ActiveWifiInfo(
                ssid = "null",
                rssi = 0,
                linkSpeed = 0,
                frequency = 0
            )
        }

    } else {
        return if (transportInfo is WifiInfo) {
            ActiveWifiInfo(
                ssid = transportInfo.ssid,
                rssi = transportInfo.rssi,
                linkSpeed = transportInfo.linkSpeed,
                frequency = transportInfo.frequency
            )

        } else {
            ActiveWifiInfo(
                ssid = "null",
                rssi = 0,
                linkSpeed = 0,
                frequency = 0
            )
        }
    }
}