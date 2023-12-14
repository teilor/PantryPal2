package br.com.teilorsoares.pantrypal.presentation.screens.main.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import br.com.teilorsoares.pantrypal.R
import br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route

sealed class MainTab(
    @DrawableRes
    val icon: Int,
    @StringRes
    val title: Int,
    @StringRes
    val toolbar: Int,
    val route: br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route
) {
    data object Home : MainTab(
        icon = R.drawable.ic_home,
        title = R.string.screen_main_bottom_navigation_home,
        toolbar = R.string.screen_main_toolbar_home,
        route = br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Home
    )

    data object Location : MainTab(
        icon = R.drawable.ic_location,
        title = R.string.screen_main_bottom_navigation_location,
        toolbar = R.string.screen_main_toolbar_location,
        route = br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Location
    )

    data object Meal : MainTab(
        icon = R.drawable.ic_meal,
        title = R.string.screen_main_bottom_navigation_meal,
        toolbar = R.string.screen_main_toolbar_meal,
        route = br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Meal
    )

    data object ShoppingList : MainTab(
        icon = R.drawable.ic_shopping_list,
        title = R.string.screen_main_bottom_navigation_shopping_list,
        toolbar = R.string.screen_main_toolbar_shopping_list,
        route = br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.ShoppingList
    )

    companion object {
        fun fromRoute(route: br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route): MainTab? {
            return when (route) {
                br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Home -> Home
                br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Location -> Location
                br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Meal -> Meal
                br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.ShoppingList -> ShoppingList
                else -> null
            }
        }

        fun values() = listOf(
            Home,
            Location,
            Meal,
            ShoppingList
        )

        fun routes() = values().map { it.route.value }
    }
}
