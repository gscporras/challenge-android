package io.parrotsoftware.qatest.repository

import androidx.annotation.WorkerThread
import io.parrotsoftware.qatest.persistence.AuthDao
import kotlinx.coroutines.flow.flow

class MainRepository constructor(
    private val authDao: AuthDao
): Repository {

    @WorkerThread
    fun isLoggedIn() = flow {
        val isLoggedIn = authDao.getCredentials()?.access?.isNotBlank() ?: false
        emit(isLoggedIn)
    }
}