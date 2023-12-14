package br.com.teilorsoares.pantrypal.presentation.screens.test.deleteditems

import br.com.teilorsoares.pantrypal.domain.model.Food
import br.com.teilorsoares.pantrypal.presentation.arch.state.UiState

data class DeletedItemsUiState(
    val foods: List<Food> = emptyList()
) : UiState
