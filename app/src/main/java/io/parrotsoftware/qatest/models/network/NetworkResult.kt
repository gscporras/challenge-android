package io.parrotsoftware.qatest.models.network

import com.google.gson.Gson
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import io.parrotsoftware.qatest.utils.ERROR_CONNECTION
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.UnknownHostException

class NetworkResult<T>(val result: T? = null, val networkError: NetworkError? = null) {
    val isError: Boolean
        get() = networkError != null

    val requiredResult: T
        get() = result!!

}

suspend fun <T> safeApiCall(apiCall: suspend () -> T): NetworkResult<T> {
    return withContext(Dispatchers.IO) {
        try {
            val response = apiCall.invoke()
            return@withContext NetworkResult(result = response)
        } catch (throwable: Throwable) {
            return@withContext NetworkResult<T>(networkError = createError(throwable))
        }
    }
}

private fun createError(throwable: Throwable): NetworkError {
    return when (throwable) {
        is HttpException -> {
            val bodyResponse: String? = throwable.response()?.errorBody()?.string()
            return try {
                val gson = Gson()
                val error = gson.fromJson(bodyResponse, LoginErrorResponse::class.java)
                NetworkError(
                    NetworkErrorType.API_ERROR,
                    error.validation?.email?.firstOrNull()?.message ?: error.errors?.firstOrNull()?.message,
                    throwable.code().toString(),
                    parseErrorBody(bodyResponse)
                )
            } catch (ex: Exception) {
                print(ex.message)
                NetworkError(
                    NetworkErrorType.API_ERROR,
                    ex.message,
                    throwable.code().toString(),
                    parseErrorBody(bodyResponse)
                )
            }
        }
        is UnknownHostException -> {
            NetworkError(NetworkErrorType.CONNECTION_ERROR, ERROR_CONNECTION)
        }
        is JsonDataException -> {
            NetworkError(NetworkErrorType.API_ERROR, throwable.message)
        }
        else -> {
            NetworkError(NetworkErrorType.UNKNOWN_ERROR, throwable.message)
        }
    }
}

private fun parseErrorBody(bodyResponse: String?): APIError? {
    return try {
        bodyResponse?.let {
            val moshiAdapter = Moshi.Builder().build().adapter(APIError::class.java)
            moshiAdapter.fromJson(it)
        }
    } catch (exception: Exception) {
        null
    }
}