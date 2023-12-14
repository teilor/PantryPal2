package br.com.teilorsoares.pantrypal.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.teilorsoares.pantrypal.data.database.model.FoodEntity
import br.com.teilorsoares.pantrypal.data.database.model.LocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(food: FoodEntity)

    // Delete food by foodId
    @Query("DELETE FROM food_table WHERE id = :foodId")
    suspend fun deleteFood(foodId: String)

    // Get all food with location
    @Query(
        "SELECT * FROM food_table " +
        "WHERE food_table.consumedOrDiscarded = :consumedOrDiscarded " +
        "ORDER BY food_table.expiryDate ASC"
    )
    fun getAllFoodByExpire(consumedOrDiscarded: Boolean): Flow<List<FoodEntity>>

    // Get all food by deleteDate
    @Query(
        "SELECT * FROM food_table " +
        "WHERE food_table.deletedDate IS NOT NULL " +
        "ORDER BY food_table.deletedDate DESC"
    )
fun getAllFoodByDelete(): Flow<List<FoodEntity>>

    // Update the food quantity by foodId
    @Query(
        "UPDATE food_table " +
        "SET quantity = :quantity " +
        "WHERE id = :foodId"
    )
    suspend fun updateFoodQuantity(foodId: String, quantity: Long)

    // Update the food consumedOrDiscarded by foodId
    @Query(
        "UPDATE food_table " +
        "SET consumedOrDiscarded = :consumedOrDiscarded " +
        "WHERE id = :foodId"
    )
    suspend fun updateFoodConsumedOrDiscarded(foodId: String, consumedOrDiscarded: Boolean)

    // Update the food deletedDate by foodId
    @Query(
        "UPDATE food_table " +
        "SET deletedDate = :deletedDate " +
        "WHERE id = :foodId"
    )
    suspend fun updateFoodDeletedDate(foodId: String, deletedDate: Long)
}
