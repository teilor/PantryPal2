package br.com.teilorsoares.pantrypal.data.repository

import br.com.teilorsoares.pantrypal.data.database.dao.ShoppingItemDao
import br.com.teilorsoares.pantrypal.data.database.model.ShoppingItemEntity
import br.com.teilorsoares.pantrypal.domain.model.ShoppingItem
import br.com.teilorsoares.pantrypal.domain.repository.ShoppingItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ShoppingItemRepositoryImpl(
    private val shoppingItemDao: ShoppingItemDao
) : ShoppingItemRepository {
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItemDao.insertShoppingItem(
            ShoppingItemEntity(
                id = shoppingItem.id,
                name = shoppingItem.name,
                purchased = shoppingItem.purchased
            )
        )
    }

    override suspend fun updateShoppingItemPurchased(id: String, purchased: Boolean) {
        shoppingItemDao.updateShoppingItemPurchased(id, purchased)
    }

    override fun getShoppingItems(purchased: Boolean): Flow<List<ShoppingItem>> {
        return shoppingItemDao.getAllShoppingItems(purchased).map { list ->
            list.map { entity ->
                ShoppingItem(
                    id = entity.id,
                    name = entity.name,
                    purchased = entity.purchased
                )
            }
        }
    }

    override suspend fun deleteShoppingItem(id: String) {
        shoppingItemDao.deleteShoppingItem(id)
    }
}