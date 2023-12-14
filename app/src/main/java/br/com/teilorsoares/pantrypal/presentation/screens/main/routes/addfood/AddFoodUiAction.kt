package br.com.teilorsoares.pantrypal.presentation.screens.main.routes.addfood

import br.com.teilorsoares.pantrypal.presentation.arch.action.UiAction
import br.com.teilorsoares.pantrypal.presentation.util.PictureEvent

sealed class AddFoodUiAction : UiAction {
    data class TakePhoto(val source: PictureEvent.PictureSource) : AddFoodUiAction()

    data object NavigateToAddLocationScreen : AddFoodUiAction()
    data object NavigateToBack : AddFoodUiAction()
    data object MissingFields : AddFoodUiAction()
    data object ShowError : AddFoodUiAction()
}
