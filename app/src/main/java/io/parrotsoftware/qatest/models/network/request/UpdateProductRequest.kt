package io.parrotsoftware.qatest.models.network.request

import io.parrotsoftware.qatest.models.entities.ProductAvailability

data class UpdateProductRequest(
    val availability: ProductAvailability
)