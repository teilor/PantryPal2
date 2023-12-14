package br.com.teilorsoares.pantrypal.domain.usecase.food

import br.com.teilorsoares.pantrypal.domain.model.Food
import br.com.teilorsoares.pantrypal.domain.model.Location
import kotlinx.coroutines.flow.Flow

fun interface GetFoodSortedByLocationUseCase : suspend (Boolean) -> Flow<Map<Location, List<Food>>>