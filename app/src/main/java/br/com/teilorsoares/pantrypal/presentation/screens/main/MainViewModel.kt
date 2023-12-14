package br.com.teilorsoares.pantrypal.presentation.screens.main

import androidx.lifecycle.viewModelScope
import br.com.teilorsoares.pantrypal.presentation.arch.ViewModel
import br.com.teilorsoares.pantrypal.presentation.screens.main.navigation.MainNavigationEvent
import br.com.teilorsoares.pantrypal.presentation.screens.main.navigation.MainTab
import br.com.teilorsoares.pantrypal.presentation.util.PictureEvent
import kotlinx.coroutines.launch
import logcat.logcat

private const val TAG = "MainViewModel"
private val mainTabRoutes = MainTab.routes()

class MainViewModel() : ViewModel<MainUiState, MainUiAction>(MainUiState()) {
    private val backStack: MutableList<br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route> = mutableListOf(
        br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Home)

    init {
        MainNavigationEvent.onNavigateTo += ::onNavigateTo
        MainNavigationEvent.onNavigateBack += ::onNavigateBack
        PictureEvent.onTakePicture += ::onTakePicture
    }

    private fun onNavigateTo(route: br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route) {
        addRouteToBackStack(route)
        navigateTo(route)
    }

    private fun onNavigateBack(unit: Unit) {
        clickedBack()
    }


    private fun onTakePicture(source: PictureEvent.PictureSource) {
        viewModelScope.launch {
            sendAction { MainUiAction.TakePicture(source) }
        }
    }

    fun clickedTab(tab: MainTab) {
        logcat(TAG) { "clickedTab: $tab" }
        viewModelScope.launch {
            if (tab.route.value != state.value.route.value) {
                addRouteToBackStack(tab.route)
                navigateTo(tab.route)
            }
        }
    }

    fun clickedAdd() {
        logcat(TAG) { "clickedAdd" }
        viewModelScope.launch {
            val route = if (state.value.selectedTab == MainTab.ShoppingList)
                br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.AddShoppingItem
            else
                br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.AddFood
            addRouteToBackStack(route)
            navigateTo(route)
        }
    }

    fun clickedBack() {
        logcat(TAG) { "clickedBack" }
        viewModelScope.launch {
            if (backStack.size > 1) {
                backStack.removeLast()
                val route = backStack.last()
                navigateTo(route)
            } else {
                sendAction { MainUiAction.FinishScreen }
            }
        }
    }

    fun clickedNavigationActivity(activity: MainNavigationEvent.Activities) {
        viewModelScope.launch {
            sendAction { MainUiAction.NavigateToActivity(activity) }
        }
    }

    private fun navigateTo(route: br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route) {
        viewModelScope.launch {
            logcat(TAG) { "navigateTo: $route" }
            val selectedTab = MainTab.fromRoute(route)
            setState { state ->
                state.copy(
                    route = route,
                    isBottomNavigationVisible = route.isBottomNavigationVisible,
                    isAddVisible = route.isAddVisible,
                    isMenuVisible = route.isMenuVisible,
                    selectedTab = selectedTab ?: state.selectedTab
                )
            }
            sendAction { MainUiAction.NavigateTo(route.value) }
        }
    }

    private fun addRouteToBackStack(route: br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route) {
        if(route == br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Home) {
            backStack.clear()
        } else if(route.value in mainTabRoutes){
            backStack.removeIf { it.value !in mainTabRoutes }
        }

        backStack.removeIf { it == route }
        backStack.add(route)
    }

    override fun onCleared() {
        super.onCleared()
        MainNavigationEvent.onNavigateTo -= ::onNavigateTo
        MainNavigationEvent.onNavigateBack -= ::onNavigateBack
        PictureEvent.onTakePicture -= ::onTakePicture
    }
}