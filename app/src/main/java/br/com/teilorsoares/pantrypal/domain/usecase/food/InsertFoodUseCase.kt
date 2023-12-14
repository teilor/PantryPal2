package br.com.teilorsoares.pantrypal.domain.usecase.food

import br.com.teilorsoares.pantrypal.domain.model.Food
import br.com.teilorsoares.pantrypal.domain.model.ShoppingItem

fun interface InsertFoodUseCase : suspend (Food) -> Unit