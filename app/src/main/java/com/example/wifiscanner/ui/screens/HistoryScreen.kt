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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wifiscanner.R
import com.example.wifiscanner.ui.WifiScannerViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.util.Date


@Composable
fun HistoryScreen(
    viewModel: WifiScannerViewModel,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "history_list") {
        composable("history_list") {
            HistoryList(viewModel, navController)
        }
        composable("details/{ssid}") { backStackEntry ->
            val ssid = backStackEntry.arguments?.getString("ssid")
            if (ssid != null) {
                DetailsList(viewModel = viewModel, ssid = ssid)
            }
        }
    }
}

@Composable
fun HistoryList(
    viewModel: WifiScannerViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val historyResults by viewModel.ssidsListStream.collectAsState(initial = emptyList())

    val isRefreshing = viewModel.isRefreshing.collectAsState().value
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { viewModel.refresh(500) {  } },
        modifier = modifier
    ) {
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(8.dp)
        ) {
            items(historyResults) { result ->
                HistoryCard(
                    viewModel = viewModel,
                    navController = navController,
                    wifiSsid = result,
                    modifier = modifier
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryCard(
    viewModel: WifiScannerViewModel,
    navController: NavController,
    wifiSsid: String,
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
            modifier = modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            onClick = { navController.navigate("details/${wifiSsid}") }
        ) {
            Text(
                text = wifiSsid,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                modifier = modifier.padding(dimensionResource(R.dimen.padding_small))
            )
        }
    }
}

@Composable
fun DetailsList(
    viewModel: WifiScannerViewModel,
    ssid: String,
    modifier: Modifier = Modifier,
) {

    val historyResults by viewModel.getWifiReadingsBySsid(ssid).collectAsState(initial = emptyList())

    val isRefreshing = viewModel.isRefreshing.collectAsState().value
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { viewModel.refresh(500) {  } },
        modifier = modifier
    ) {
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(8.dp)
        ) {
            items(historyResults) { result ->
                DetailsCard(
                    wifiSsid = result.ssid,
                    date = result.date,
                    level = result.rssi,
                    frequency = result.frequency,
                    distance = result.distance
                )
            }
        }
    }
}

@Composable
fun DetailsCard(
    wifiSsid: String,
    date: Date,
    level: Int,
    frequency: Int,
    distance: Int,
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
                    text = date.toString(),
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )
                Text(text = "$level dBm")
                Text(text = "$frequency MHz")
                Text(text = "$distance m")
            }
        }
    }
}