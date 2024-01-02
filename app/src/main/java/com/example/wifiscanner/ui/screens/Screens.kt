package com.example.wifiscanner.ui.screens

sealed class Screens(val route: String) {
    object ActiveScreen : Screens("active_route")
    object ScanScreen : Screens("scan_route")
    object GraphScreen : Screens("graph_route")
    object HistoryScreen : Screens("history_route")
}