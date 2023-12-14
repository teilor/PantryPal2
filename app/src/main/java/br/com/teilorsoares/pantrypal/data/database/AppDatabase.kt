package br.com.teilorsoares.pantrypal.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.teilorsoares.pantrypal.data.database.dao.FoodDao
import br.com.teilorsoares.pantrypal.data.database.dao.LocationDao
import br.com.teilorsoares.pantrypal.data.database.dao.ShoppingItemDao
import br.com.teilorsoares.pantrypal.data.database.model.FoodEntity
import br.com.teilorsoares.pantrypal.data.database.model.LocationEntity
import br.com.teilorsoares.pantrypal.data.database.model.ShoppingItemEntity

@Database(
    entities = [
        FoodEntity::class,
        LocationEntity::class,
        ShoppingItemEntity::class
    ],
    version = 4
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao
    abstract fun locationDao(): LocationDao
    abstract fun shoppingItemDao(): ShoppingItemDao
}
