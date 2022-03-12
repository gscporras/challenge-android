package io.parrotsoftware.qatest.models.entities

import androidx.compose.runtime.Immutable

@Immutable
data class EnabledProduct(
    val product: Product = Product(),
    val enabled: Boolean = false
)