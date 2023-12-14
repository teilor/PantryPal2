package br.com.teilorsoares.pantrypal.presentation.screens.main.routes.location

import br.com.teilorsoares.pantrypal.domain.model.Food
import br.com.teilorsoares.pantrypal.presentation.arch.state.UiState

data class LocationUiState(
    val selectedTabIndex: Int = 0,
    val consumed: Boolean = false,
    val groups: List<LocationGroup> = emptyList(),
    val selectedFood: Food? = null,
    val expandedQuantity: Boolean = false,
    val expandedConsumedFood: Boolean = false
) : UiState
