package br.com.teilorsoares.pantrypal.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_table")
data class LocationEntity(
    @PrimaryKey
    val id: String,
    val name: String
)
