package io.parrotsoftware.qatest.models.network

enum class NetworkErrorType {
    CONNECTION_ERROR,
    API_ERROR,
    UNKNOWN_ERROR
}

data class APIError(
    val code: String,
    val message: String
)

data class NetworkError(
    var type: NetworkErrorType,
    var rawError: String? = null,
    var errorCode: String? = null,
    var apiError: APIError? = null
)