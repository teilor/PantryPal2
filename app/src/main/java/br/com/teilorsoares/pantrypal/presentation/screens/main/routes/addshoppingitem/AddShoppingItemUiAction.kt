package br.com.teilorsoares.pantrypal.presentation.screens.main.routes.addshoppingitem

import br.com.teilorsoares.pantrypal.presentation.arch.action.UiAction

sealed class AddShoppingItemUiAction : UiAction {
    data object FinishScreen : AddShoppingItemUiAction()
}
