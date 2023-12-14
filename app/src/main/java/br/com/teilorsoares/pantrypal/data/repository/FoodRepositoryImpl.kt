package br.com.teilorsoares.pantrypal.data.repository

import androidx.room.withTransaction
import br.com.teilorsoares.pantrypal.data.database.AppDatabase
import br.com.teilorsoares.pantrypal.data.database.dao.FoodDao
import br.com.teilorsoares.pantrypal.data.database.dao.LocationDao
import br.com.teilorsoares.pantrypal.data.database.model.FoodEntity
import br.com.teilorsoares.pantrypal.domain.model.Food
import br.com.teilorsoares.pantrypal.domain.model.Location
import br.com.teilorsoares.pantrypal.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

class FoodRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val foodDao: FoodDao,
    private val locationDao: LocationDao
) : FoodRepository {
    private val locations: MutableList<Location> = mutableListOf()

    override suspend fun insertFood(food: Food) {
        foodDao.insertFood(
            FoodEntity(
                id = food.id,
                name = food.name,
                photo = food.photo,
                createdDate = food.createdDate,
                expiryDate = food.expiryDate,
                deletedDate = food.deletedDate,
                locationId = food.location.id,
                meal = food.meal.name,
                quantity = food.quantity,
                consumedOrDiscarded = food.consumedOrDiscarded
            )
        )
    }

    override suspend fun deleteFood(foodId: String) {
        foodDao.deleteFood(foodId)
    }

    override suspend fun updateFoodQuantity(foodId: String, quantity: Long) {
        foodDao.updateFoodQuantity(foodId, quantity)
    }

    override suspend fun updateFoodConsumedOrDiscarded(
        foodId: String,
        consumedOrDiscarded: Boolean
    ) {
        appDatabase.withTransaction {
            foodDao.updateFoodConsumedOrDiscarded(foodId, consumedOrDiscarded)
            if(consumedOrDiscarded) {
                foodDao.updateFoodDeletedDate(foodId, System.currentTimeMillis())
            }
        }
    }

    override suspend fun getFoodByExpire(consumedOrDiscarded: Boolean): Flow<List<Food>> {
        val now = System.currentTimeMillis()
        // Reset time to midnight
        val cal = Calendar.getInstance()
        val today = cal.apply {
            timeInMillis = now
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        return foodDao.getAllFoodByExpire(consumedOrDiscarded).map { list ->
            list.map { foodEntity ->
                // Get location from database
                var location = locations.find { it.id == foodEntity.locationId }
                if (location == null) {
                    val locationEntity = locationDao.getLocationById(foodEntity.locationId)
                        ?: throw Exception("Location not found")

                    location = Location(
                        id = locationEntity.id,
                        name = locationEntity.name
                    )
                    locations.add(location)
                }

                // Calculate status
                val daysDiff = (foodEntity.expiryDate - today) / (1000 * 60 * 60 * 24f)
                val status = when {
                    // If daysDiff is negative, it means that the food is expired
                    daysDiff < 0-> Food.Status.EXPIRED
                    // If daysDiff is less than 3, it means that the food is expiring
                    daysDiff < 3 -> Food.Status.EXPIRING
                    // Else, it means that the food is ok
                    else -> Food.Status.OK
                }

                Food(
                    id = foodEntity.id,
                    name = foodEntity.name,
                    photo = foodEntity.photo,
                    createdDate = foodEntity.createdDate,
                    expiryDate = foodEntity.expiryDate,
                    deletedDate = foodEntity.deletedDate,
                    location = location ?: throw Exception("Location not found"),
                    meal = Food.Meal.valueOf(foodEntity.meal),
                    quantity = foodEntity.quantity,
                    consumedOrDiscarded = foodEntity.consumedOrDiscarded,
                    status = status
                )
            }
        }
    }

    override suspend fun getFoodByDelete(): Flow<List<Food>> {
        return foodDao.getAllFoodByDelete().map { list ->
            list.map { foodEntity ->
                // Get location from database
                var location = locations.find { it.id == foodEntity.locationId }
                if (location == null) {
                    val locationEntity = locationDao.getLocationById(foodEntity.locationId)
                        ?: throw Exception("Location not found")

                    location = Location(
                        id = locationEntity.id,
                        name = locationEntity.name
                    )
                    locations.add(location)
                }

                Food(
                    id = foodEntity.id,
                    name = foodEntity.name,
                    photo = foodEntity.photo,
                    createdDate = foodEntity.createdDate,
                    expiryDate = foodEntity.expiryDate,
                    deletedDate = foodEntity.deletedDate,
                    location = location ?: throw Exception("Location not found"),
                    meal = Food.Meal.valueOf(foodEntity.meal),
                    quantity = foodEntity.quantity,
                    consumedOrDiscarded = foodEntity.consumedOrDiscarded,
                    status = Food.Status.OK
                )
            }
        }
    }

    override suspend fun getFoodByLocation(consumedOrDiscarded: Boolean): Flow<Map<Location, List<Food>>> {
        return getFoodByExpire(consumedOrDiscarded).map { list ->
            list.groupBy { it.location }
        }
    }

    override suspend fun getFoodByMeal(consumedOrDiscarded: Boolean): Flow<Map<Food.Meal, List<Food>>> {
        return getFoodByExpire(consumedOrDiscarded)
            .map { list ->
                list.groupBy { it.meal }
            }
    }

}