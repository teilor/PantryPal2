package br.com.teilorsoares.pantrypal.presentation.screens.main.routes

sealed class Route(
    val value: String,
    val animation: br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Animation = br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Animation.None,
    val ordinal: Int = 0,
    val isBottomNavigationVisible: Boolean = true,
    val isMenuVisible: Boolean = true,
    val isAddVisible: Boolean = true
) {
    data object Home : br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route(
        value = "home",
        animation = br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Animation.SlideHorizontal,
        ordinal = 0
    )

    data object Location : br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route(
        value = "location",
        animation = br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Animation.SlideHorizontal,
        ordinal = 1
    )

    data object Meal : br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route(
        value = "meal",
        animation = br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Animation.SlideHorizontal,
        ordinal = 2
    )

    data object ShoppingList : br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route(
        value = "shopping_list",
        animation = br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Animation.SlideHorizontal,
        ordinal = 3
    )

    data object AddFood : br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route(
        value = "add_food",
        animation = br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Animation.SlideVertical,
        isAddVisible = false,
        isMenuVisible = false,
        isBottomNavigationVisible = false
    )

    data object AddShoppingItem : br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route(
        value = "add_shopping_item",
        animation = br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Animation.SlideVertical,
        isAddVisible = false,
        isMenuVisible = false,
        isBottomNavigationVisible = false
    )

    data object AddLocation : br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route(
        value = "add_location",
        animation = br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Animation.SlideVertical,
        isAddVisible = false,
        isMenuVisible = false,
        isBottomNavigationVisible = false
    )

    enum class Animation {
        SlideVertical,
        SlideHorizontal,
        Fade,
        None
    }

    companion object {
        fun valueOf(route: String?): br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route {
            return when (route) {
                null, br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Home.value -> br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Home
                br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Location.value -> br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Location
                br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Meal.value -> br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Meal
                br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.ShoppingList.value -> br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.ShoppingList
                br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.AddFood.value -> br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.AddFood
                br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.AddShoppingItem.value -> br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.AddShoppingItem
                br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.AddLocation.value -> br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.AddLocation
                else -> throw IllegalArgumentException("Route not found: $route")
            }
        }
    }
}
