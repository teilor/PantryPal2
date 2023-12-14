package br.com.teilorsoares.pantrypal.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.teilorsoares.pantrypal.data.database.model.LocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: LocationEntity)

    @Query("SELECT * FROM location_table")
    fun getAllLocations(): Flow<List<LocationEntity>>

    @Query("SELECT * FROM location_table WHERE id = :locationId")
    suspend fun getLocationById(locationId: String): LocationEntity?
}