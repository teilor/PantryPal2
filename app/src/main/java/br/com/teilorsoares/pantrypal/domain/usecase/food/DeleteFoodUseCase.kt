package br.com.teilorsoares.pantrypal.domain.usecase.food

fun interface DeleteFoodUseCase : suspend (String) -> Unit