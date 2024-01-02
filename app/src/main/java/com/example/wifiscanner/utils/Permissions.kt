package com.example.wifiscanner.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import androidx.core.app.ActivityCompat

fun requestPermissions(context: Context) {
    ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0)
}
