package io.parrotsoftware.qatest.models.entities

import androidx.compose.runtime.Immutable

@Immutable
data class Category(
    val uuid: String = "",
    val name: String = "",
    val sortPosition: Int = 0
)