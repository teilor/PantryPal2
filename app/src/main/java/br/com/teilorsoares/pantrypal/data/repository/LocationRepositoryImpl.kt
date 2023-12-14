package br.com.teilorsoares.pantrypal.data.repository

import br.com.teilorsoares.pantrypal.data.database.dao.LocationDao
import br.com.teilorsoares.pantrypal.data.database.model.LocationEntity
import br.com.teilorsoares.pantrypal.domain.model.Location
import br.com.teilorsoares.pantrypal.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocationRepositoryImpl(
    private val locationDao: LocationDao
) : LocationRepository {
    override suspend fun insertLocation(location: Location) {
        locationDao.insertLocation(
            LocationEntity(
                id = location.id,
                name = location.name
            )
        )
    }

    override suspend fun getLocations(): Flow<List<Location>> {
        return locationDao.getAllLocations().map { list ->
            list.map { entity ->
                Location(
                    id = entity.id,
                    name = entity.name
                )
            }
        }
    }
}