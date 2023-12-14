package br.com.teilorsoares.pantrypal.presentation.screens.main

import br.com.teilorsoares.pantrypal.presentation.arch.action.UiAction
import br.com.teilorsoares.pantrypal.presentation.screens.main.navigation.MainNavigationEvent
import br.com.teilorsoares.pantrypal.presentation.util.PictureEvent

sealed class MainUiAction : UiAction {
    data class NavigateTo(val route: String) : MainUiAction()
    data class NavigateToActivity(val activities: MainNavigationEvent.Activities) : MainUiAction()
    data object FinishScreen : MainUiAction()
    data class TakePicture(val source: PictureEvent.PictureSource) : MainUiAction()
}
