package br.com.teilorsoares.pantrypal.presentation.screens.main.routes.addlocation

import br.com.teilorsoares.pantrypal.presentation.arch.action.UiAction

sealed class AddLocationUiAction : UiAction {
    data object FinishScreen : AddLocationUiAction()
}
