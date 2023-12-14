package br.com.teilorsoares.pantrypal.presentation.screens.main.routes.meal

import br.com.teilorsoares.pantrypal.domain.model.Food

data class MealGroup(
    val meal: Food.Meal,
    val foods: List<Food>
)
