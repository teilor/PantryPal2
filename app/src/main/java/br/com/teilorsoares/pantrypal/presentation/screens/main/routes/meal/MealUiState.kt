package br.com.teilorsoares.pantrypal.presentation.screens.main.routes.meal

import br.com.teilorsoares.pantrypal.domain.model.Food
import br.com.teilorsoares.pantrypal.presentation.arch.state.UiState

data class MealUiState(
    val selectedTabIndex: Int = 0,
    val consumed: Boolean = false,
    val groups: List<MealGroup> = emptyList(),
    val selectedFood: Food? = null,
    val expandedQuantity: Boolean = false,
    val expandedConsumedFood: Boolean = false
) : UiState
