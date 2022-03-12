package io.parrotsoftware.qatest.models.network

data class LoginErrorResponse(
    val errors: List<Error>? = null,
    val validation: Validation? = null
)

data class Error(
    val code: String,
    val message: String
)

data class Validation(
    val email: List<Email>?
)

data class Email(
    val code: String,
    val message: String
)