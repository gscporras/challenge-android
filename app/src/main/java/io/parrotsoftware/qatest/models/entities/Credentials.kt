package io.parrotsoftware.qatest.models.entities

import androidx.compose.runtime.Immutable
import androidx.room.Entity

@Immutable
@Entity(primaryKeys = [("access")])
data class Credentials(
    val access: String = "",
    val refresh: String = ""
)