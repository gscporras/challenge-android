package io.parrotsoftware.qatest.models.entities

import androidx.compose.runtime.Immutable

@Immutable
data class UserWithStores(
    val uuid: String = "",
    val email: String = "",
    val stores: List<Store>,
    val username: String = ""
)