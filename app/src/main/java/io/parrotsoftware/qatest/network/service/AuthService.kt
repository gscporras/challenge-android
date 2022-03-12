package io.parrotsoftware.qatest.network.service

import io.parrotsoftware.qatest.mapper.ApiResponseMapper
import io.parrotsoftware.qatest.models.entities.Credentials
import io.parrotsoftware.qatest.models.entities.UserWithStores
import io.parrotsoftware.qatest.models.network.request.AuthRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("/api/auth/token")
    suspend fun auth(
        @Body authRequest: AuthRequest
    ): Credentials?

    @GET("/api/v1/users/me")
    suspend fun getMe(
        @Header("Authorization") access: String
    ): ApiResponseMapper<UserWithStores>
}