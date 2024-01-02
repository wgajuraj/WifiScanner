package com.example.wifiscanner.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wifiscanner.R
import com.example.wifiscanner.data.WifiReading
import com.example.wifiscanner.ui.screens.BottomNavigationItem
import com.example.wifiscanner.ui.screens.Screens
import com.example.wifiscanner.ui.screens.ActiveScreen
import com.example.wifiscanner.ui.screens.GraphScreen
import com.example.wifiscanner.ui.screens.HistoryScreen
import com.example.wifiscanner.ui.screens.ScanScreen
import com.example.wifiscanner.utils.dampingInFreeSpace

@Composable
fun WifiScannerApp(
    viewModel: WifiScannerViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {

    var navigationSelectedItem by remember { mutableIntStateOf(0) }
    val navController = rememberNavController()


    LaunchedEffect(viewModel.wifiScanner.scanResults) {
        viewModel.wifiScanner.scanResults.collect { results ->
            results?.forEach { result ->
                val ssid = result.SSID
                if (ssid != "") {
                    val wifiReading = WifiReading(
                        ssid = ssid,
                        rssi = result.level,
                        frequency = result.frequency,
                        distance = dampingInFreeSpace(result.frequency, result.level)
                    )

                    viewModel.updateCurrentReading(wifiReading)
                    viewModel.saveWifiReading()
                }
            }
        }
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { WifiScannerTopAppBar() },
        bottomBar = {
            NavigationBar {
                BottomNavigationItem().bottomNavigationItems().forEachIndexed { index, navigationItem->
                    NavigationBarItem(
                        selected = index == navigationSelectedItem,
                        label = { Text(navigationItem.label) },
                        icon = { Icon(navigationItem.icon, contentDescription = navigationItem.label) },
                        onClick = {
                            navigationSelectedItem = index
                            navController.navigate(navigationItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.ActiveScreen.route,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            composable(Screens.ActiveScreen.route) {
                ActiveScreen(viewModel)
            }
            composable(Screens.ScanScreen.route) {
                ScanScreen(viewModel)
            }
            composable(Screens.GraphScreen.route) {
                GraphScreen(viewModel)
            }
            composable(Screens.HistoryScreen.route) {
                HistoryScreen(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WifiScannerTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        title = {
            Text(
                text = stringResource(R.string.app_name),
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium)),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    )
}

@Composable
fun screen1(
    modifier: Modifier = Modifier
) {
    Text(text = "Screen 1")
}

@Composable
fun screen2(
    modifier: Modifier = Modifier
) {
    Text(text = "Screen 2")
}

@Composable
fun screen3(
    modifier: Modifier = Modifier
) {
    Text(text = "Screen 3")
}

@Composable
fun screen4(
    modifier: Modifier = Modifier
) {
    Text(text = "Screen 4")
}