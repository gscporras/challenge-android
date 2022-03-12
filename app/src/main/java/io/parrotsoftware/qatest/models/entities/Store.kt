package io.parrotsoftware.qatest.models.entities

import androidx.compose.runtime.Immutable
import androidx.room.Entity

@Immutable
@Entity(primaryKeys = [("uuid")])
data class Store(
    val uuid: String = "",
    val name: String = ""
)