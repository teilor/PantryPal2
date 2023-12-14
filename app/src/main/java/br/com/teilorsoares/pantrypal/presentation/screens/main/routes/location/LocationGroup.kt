package br.com.teilorsoares.pantrypal.presentation.screens.main.routes.location

import br.com.teilorsoares.pantrypal.domain.model.Food

data class LocationGroup(
    val title: String,
    val foods: List<Food>
)
