package io.parrotsoftware.qatest.ui.login.viewmodel

data class LoginViewState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    var navigateToHome: Boolean = false
)