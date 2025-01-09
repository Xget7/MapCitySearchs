package dev.xget.ualachallenge.presentation.screens

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object FullMapsScreen : Screen("full_maps")
}