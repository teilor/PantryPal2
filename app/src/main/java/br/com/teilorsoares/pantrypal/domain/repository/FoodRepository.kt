package br.com.teilorsoares.pantrypal.domain.repository

import br.com.teilorsoares.pantrypal.domain.model.Food
import br.com.teilorsoares.pantrypal.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    suspend fun insertFood(food: Food)
    suspend fun deleteFood(foodId: String)

    suspend fun updateFoodQuantity(foodId: String, quantity: Long)

    suspend fun updateFoodConsumedOrDiscarded(foodId: String, consumedOrDiscarded: Boolean)

    suspend fun getFoodByExpire(consumedOrDiscarded: Boolean) : Flow<List<Food>>
    suspend fun getFoodByDelete() : Flow<List<Food>>
    suspend fun getFoodByLocation(consumedOrDiscarded: Boolean) : Flow<Map<Location, List<Food>>>
    suspend fun getFoodByMeal(consumedOrDiscarded: Boolean) : Flow<Map<Food.Meal, List<Food>>>
}