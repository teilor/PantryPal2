package br.com.teilorsoares.pantrypal.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_item_table")
data class ShoppingItemEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val purchased: Boolean
)
