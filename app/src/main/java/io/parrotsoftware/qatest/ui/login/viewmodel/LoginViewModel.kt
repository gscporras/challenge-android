package io.parrotsoftware.qatest.ui.login.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.parrotsoftware.qatest.BuildConfig
import io.parrotsoftware.qatest.repository.AuthRepository
import io.parrotsoftware.qatest.utils.ENTER_EMAIL_ID
import io.parrotsoftware.qatest.utils.ENTER_PASSWORD
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _state = MutableStateFlow(LoginViewState())

    val state: StateFlow<LoginViewState>
        get() = _state

    val email = MutableLiveData(BuildConfig.EMAIL_TEST)
    val password = MutableLiveData(BuildConfig.PASSWORD_TEST)

    init {
        _state.value = LoginViewState(
            isLoading = false,
            errorMessage = null
        )
    }

    fun signIn(email: String, password: String) {
        _state.value = LoginViewState(
            isLoading = true,
            errorMessage = null
        )
        when {
            email.isEmpty() -> {
                _state.value = LoginViewState(
                    isLoading = false,
                    errorMessage = ENTER_EMAIL_ID
                )
            }
            password.isEmpty() -> {
                _state.value = LoginViewState(
                    isLoading = false,
                    errorMessage = ENTER_PASSWORD
                )
            }
            else -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val result = authRepository.signIn(email, password)
                    _state.value = LoginViewState(
                        isLoading = result.isLoading,
                        errorMessage = result.errorMessage,
                        navigateToHome = result.navigateToHome
                    )
                }
            }
        }
    }
}