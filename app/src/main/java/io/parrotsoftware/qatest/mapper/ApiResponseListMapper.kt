package io.parrotsoftware.qatest.mapper

data class ApiResponseListMapper<T>(
    val status: String,
    val results: List<T>
)