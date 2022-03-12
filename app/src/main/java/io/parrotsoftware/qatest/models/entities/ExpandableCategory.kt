package io.parrotsoftware.qatest.models.entities

import androidx.compose.runtime.Immutable

@Immutable
data class ExpandableCategory(
    val category: Category = Category(),
    val expanded: Boolean = false,
    val products: List<EnabledProduct> = mutableListOf()
)