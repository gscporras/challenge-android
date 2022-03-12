package io.parrotsoftware.qatest.models.entities

import androidx.compose.runtime.Immutable
import androidx.room.Entity

@Immutable
@Entity(primaryKeys = [("uuid")])
data class Product(
    val uuid: String = "",
    val name: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val price: String = "",
    var availability: ProductAvailability = ProductAvailability.UNAVAILABLE,
    val category: Category = Category()
)