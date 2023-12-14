package br.com.teilorsoares.pantrypal.domain.repository

import br.com.teilorsoares.pantrypal.domain.model.ShoppingItem
import kotlinx.coroutines.flow.Flow

interface ShoppingItemRepository {
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)
    suspend fun updateShoppingItemPurchased(id: String, purchased: Boolean)
    fun getShoppingItems(purchased: Boolean): Flow<List<ShoppingItem>>

    suspend fun deleteShoppingItem(id: String)
}