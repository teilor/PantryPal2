package br.com.teilorsoares.pantrypal.presentation.screens.main.routes.shoppinglist

import br.com.teilorsoares.pantrypal.domain.model.ShoppingItem
import br.com.teilorsoares.pantrypal.presentation.arch.state.UiState

data class ShoppingListUiState(
    val selectedTabIndex: Int = 0,
    val purchased: Boolean = false,
    val shoppingItems: List<ShoppingItem> = emptyList()
) : UiState