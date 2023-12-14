package br.com.teilorsoares.pantrypal.domain.usecase.food

import br.com.teilorsoares.pantrypal.domain.model.Food
import kotlinx.coroutines.flow.Flow

fun interface GetFoodSortedByMealUseCase : suspend (Boolean) -> Flow<Map<Food.Meal, List<Food>>>