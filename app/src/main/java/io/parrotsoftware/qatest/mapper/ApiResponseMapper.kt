package io.parrotsoftware.qatest.mapper

data class ApiResponseMapper<T>(
    val status: String,
    val result: T
)