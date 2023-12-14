package br.com.teilorsoares.pantrypal.domain.usecase.location

import br.com.teilorsoares.pantrypal.domain.model.Location
import kotlinx.coroutines.flow.Flow

fun interface GetLocationsUseCase : suspend () -> Flow<List<Location>>