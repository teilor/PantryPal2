package br.com.teilorsoares.pantrypal.domain.usecase.food

fun interface UpdateFoodDiscardUseCase : suspend (String, Boolean) -> Unit