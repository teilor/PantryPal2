package br.com.teilorsoares.pantrypal.presentation.screens.main.routes.addshoppingitem

import br.com.teilorsoares.pantrypal.presentation.arch.state.UiState

data class AddShoppingItemUiState(
    val shoppingItemName: String = ""
) : UiState
