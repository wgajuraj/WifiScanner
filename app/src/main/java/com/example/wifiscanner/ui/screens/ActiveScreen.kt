package com.example.wifiscanner.ui.screens

import android.net.wifi.WifiInfo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.wifiscanner.R
import com.example.wifiscanner.ui.WifiScannerViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun ActiveScreen(
    viewModel: WifiScannerViewModel,
    modifier: Modifier = Modifier
) {

    val activeConnectionInfo by remember { mutableStateOf(viewModel.activeConnectionInfo) }
    val isRefreshing = viewModel.isRefreshing.collectAsState().value
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { viewModel.refresh(500) {  } },
        modifier = modifier
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = "SSID: ${activeConnectionInfo.value?.ssid}")
                Text(text = "RSSI: ${activeConnectionInfo.value?.rssi} dBm")
                Text(text = "Link Speed: ${activeConnectionInfo.value?.linkSpeed} mbps")
                Text(text = "Frequency: ${activeConnectionInfo.value?.frequency} MHz")
            }
        }
    }
}