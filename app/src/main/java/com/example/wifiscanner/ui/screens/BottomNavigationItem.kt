package com.example.wifiscanner.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.filled.WifiFind
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val label : String = "",
    val icon : ImageVector = Icons.Filled.Home,
    val route : String = ""
) {
    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Active",
                icon = Icons.Filled.Wifi,
                route = Screens.ActiveScreen.route
            ),
            BottomNavigationItem(
                label = "Scan",
                icon = Icons.Filled.WifiFind,
                route = Screens.ScanScreen.route
            ),
            BottomNavigationItem(
                label = "Graph",
                icon = Icons.Filled.ShowChart,
                route = Screens.GraphScreen.route
            ),
            BottomNavigationItem(
                label = "History",
                icon = Icons.Filled.History,
                route = Screens.HistoryScreen.route
            )
        )
    }
}
