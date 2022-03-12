package io.parrotsoftware.qatest.repository

import io.parrotsoftware.qatest.models.network.request.AuthRequest
import io.parrotsoftware.qatest.models.network.safeApiCall
import io.parrotsoftware.qatest.network.service.AuthService
import io.parrotsoftware.qatest.persistence.AuthDao
import io.parrotsoftware.qatest.ui.login.viewmodel.LoginViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository constructor(
    private val authService: AuthService,
    private val authDao: AuthDao
): Repository {

    suspend fun getStoreName() =
        withContext(Dispatchers.IO) {
            return@withContext authDao.getStore()?.name
        }

    suspend fun signIn(email: String, password: String): LoginViewState =
        withContext(Dispatchers.IO) {
            val responseAuth = safeApiCall {
                authService.auth(AuthRequest(email, password))
            }

            if (responseAuth.isError) {
                return@withContext LoginViewState(errorMessage = responseAuth.networkError?.rawError, isLoading = false)
            }
            val credentials = responseAuth.requiredResult
            credentials?.let { authDao.saveCredentials(credentials) }
            val responseUser = safeApiCall {
                authService.getMe("Bearer ${credentials?.access}")
            }

            if (responseUser.isError) {
                return@withContext LoginViewState(errorMessage = responseUser.networkError?.rawError, isLoading = false)
            }

            val user = responseUser.requiredResult.result
            if (user.stores.isEmpty()) {
                return@withContext LoginViewState(errorMessage = "No hay tiendas disponibles", isLoading = false)
            }

            val store = user.stores.firstOrNull()
            store?.let { authDao.insertStore(store) }
            return@withContext LoginViewState(navigateToHome = true, isLoading = false)
        }

    suspend fun signOut() =
        withContext(Dispatchers.IO) {
            authDao.deleteCredentials()
            authDao.deleteStore()
        }
}