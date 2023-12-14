package br.com.teilorsoares.pantrypal.data.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "food_table")
data class FoodEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val photo: String,
    val createdDate: Long,
    val expiryDate: Long,
    val deletedDate: Long?,
    val locationId: String,
    val meal: String,
    val quantity: Long,
    val consumedOrDiscarded: Boolean
)
