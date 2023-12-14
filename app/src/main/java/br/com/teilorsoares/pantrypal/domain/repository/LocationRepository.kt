package br.com.teilorsoares.pantrypal.domain.repository

import br.com.teilorsoares.pantrypal.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    suspend fun insertLocation(location: Location)
    suspend fun getLocations(): Flow<List<Location>>
}