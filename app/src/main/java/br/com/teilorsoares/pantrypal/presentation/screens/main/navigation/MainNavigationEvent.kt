package br.com.teilorsoares.pantrypal.presentation.screens.main.navigation

import br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route
import br.com.teilorsoares.pantrypal.presentation.util.Event

object MainNavigationEvent {
    val onNavigateTo = Event<br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route>()
    val onNavigateBack = Event<Unit>()

    fun navigateTo(route: br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route) {
        onNavigateTo(route)
    }

    fun navigateBack() {
        onNavigateBack(Unit)
    }

    enum class Activities {
        Activity01, Activity02
    }
}