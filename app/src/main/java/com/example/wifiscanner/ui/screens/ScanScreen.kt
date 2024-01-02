package com.example.wifiscanner.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.wifiscanner.R
import com.example.wifiscanner.ui.WifiScannerViewModel
import com.example.wifiscanner.utils.dampingInFreeSpace
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Composable
fun ScanScreen(
    viewModel: WifiScannerViewModel,
    modifier: Modifier = Modifier,
) {
    val scanResults by viewModel.scanResults.collectAsState()

    val isRefreshing = viewModel.isRefreshing.collectAsState().value
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { viewModel.refresh(500) { viewModel.wifiScanner.startScan() } },
        modifier = modifier
    ) {
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(8.dp)
        ) {
            items(scanResults?.sortedByDescending { result -> result.level } ?: listOf()) { scanResult ->
                ScanCard(
                    wifiSsid = scanResult.SSID,
                    level = scanResult.level,
                    frequency = scanResult.frequency
                )
            }
        }
    }
}

@Composable
fun ScanCard(
    wifiSsid: String,
    level: Int,
    frequency: Int,
    modifier: Modifier = Modifier,

    ) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = dimensionResource(R.dimen.padding_small),
                horizontal = dimensionResource(R.dimen.padding_medium)
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = wifiSsid,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )
                Text(text = "$level dBm")
                Text(text = "${dampingInFreeSpace(frequency, level)} m")
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun ScanScreenPreview() {
//    MaterialTheme {
//        ScanScreen()
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun ScanListItemPreview() {
//    MaterialTheme {
//        ScanCard()
//    }
//}