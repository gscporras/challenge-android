package io.parrotsoftware.qatest.network.service

import io.parrotsoftware.qatest.mapper.ApiResponseListMapper
import io.parrotsoftware.qatest.mapper.ApiResponseMapper
import io.parrotsoftware.qatest.models.entities.Product
import io.parrotsoftware.qatest.models.network.request.UpdateProductRequest
import retrofit2.http.*

interface ProductService {

    @GET("/api/v1/products/")
    suspend fun getProducts(
        @Header("Authorization") access: String,
        @Query("store") storeId: String
    ): ApiResponseListMapper<Product>

    @PUT("/api/v1/products/{product_id}/availability")
    suspend fun updateProduct(
        @Header("Authorization") access: String,
        @Path("product_id") productId: String,
        @Body request: UpdateProductRequest
    ): ApiResponseMapper<Product>
}