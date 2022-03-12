package io.parrotsoftware.qatest.ui.main

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import io.parrotsoftware.qatest.extensions.popUpToTop
import io.parrotsoftware.qatest.ui.home.HomeScreen
import io.parrotsoftware.qatest.ui.login.LoginScreen
import io.parrotsoftware.qatest.ui.main.viewmodel.MainViewModel
import io.parrotsoftware.qatest.ui.navigation.NavScreen

@Composable
fun MainScreen(
    viewModel: MainViewModel
) {
    val navController = rememberNavController()
    val initialRoute = if (viewModel.isLoggedIn.value) NavScreen.Home else NavScreen.Login

    ProvideWindowInsets {
        NavHost(navController = navController, startDestination = initialRoute.route) {
            composable(
                route = NavScreen.Login.route,
                arguments = emptyList()
            ) {
                LoginScreen(
                    viewModel = hiltViewModel(),
                    navigateToHome = {
                        navController.navigate(NavScreen.Home.route) {
                            popUpToTop(navController)
                        }
                    }
                )
            }
            composable(
                route = NavScreen.Home.route,
                arguments = emptyList()
            ) {
                HomeScreen(
                    viewModel = hiltViewModel(),
                    navigateToLogin = {
                        navController.navigate(NavScreen.Login.route)
                    }
                )
            }
        }
    }
}