package io.parrotsoftware.qatest.ui.navigation

import androidx.compose.runtime.Immutable

@Immutable
sealed class NavScreen(val route: String) {

    object Login : NavScreen("Login")

    object Home : NavScreen("Home")
}