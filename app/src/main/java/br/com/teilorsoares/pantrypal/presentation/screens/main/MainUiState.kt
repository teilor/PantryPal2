package br.com.teilorsoares.pantrypal.presentation.screens.main

import br.com.teilorsoares.pantrypal.presentation.arch.state.UiState
import br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route
import br.com.teilorsoares.pantrypal.presentation.screens.main.navigation.MainTab

data class MainUiState(
    // Navigation
    val route: br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route = br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Home,
    // Bottom Navigation Bar
    val selectedTab: MainTab = MainTab.Home,
    val tabs: List<MainTab> = listOf(
        MainTab.Home,
        MainTab.Location,
        MainTab.Meal,
        MainTab.ShoppingList
    ),
    val isBottomNavigationVisible: Boolean = true,
    val isMenuVisible: Boolean = true,
    val isAddVisible: Boolean = true
) : UiState
