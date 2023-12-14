package br.com.teilorsoares.pantrypal.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.teilorsoares.pantrypal.data.database.model.ShoppingItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(shoppingItem: ShoppingItemEntity)

    @Query("UPDATE shopping_item_table SET purchased = :purchased WHERE id = :id")
    suspend fun updateShoppingItemPurchased(id: String, purchased: Boolean)

    @Query("SELECT * FROM shopping_item_table WHERE purchased = :purchased")
    fun getAllShoppingItems(purchased: Boolean): Flow<List<ShoppingItemEntity>>

    @Query("DELETE FROM shopping_item_table WHERE id = :id")
    suspend fun deleteShoppingItem(id: String)
}