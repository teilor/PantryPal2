package br.com.teilorsoares.pantrypal.presentation.screens.main.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.teilorsoares.pantrypal.presentation.screens.main.MainActivity
import br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route
import br.com.teilorsoares.pantrypal.presentation.screens.main.routes.addfood.AddFoodScreen
import br.com.teilorsoares.pantrypal.presentation.screens.main.routes.addlocation.AddLocationScreen
import br.com.teilorsoares.pantrypal.presentation.screens.main.routes.addshoppingitem.AddShoppingItemScreen
import br.com.teilorsoares.pantrypal.presentation.screens.main.routes.home.HomeScreen
import br.com.teilorsoares.pantrypal.presentation.screens.main.routes.location.LocationScreen
import br.com.teilorsoares.pantrypal.presentation.screens.main.routes.meal.MealScreen
import br.com.teilorsoares.pantrypal.presentation.screens.main.routes.shoppinglist.ShoppingListScreen
import org.koin.androidx.viewmodel.ext.android.viewModel


@Composable
fun MainActivity.MainNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Home.value,
        modifier = modifier
    ) {
        screen(route = Route.Home) {
            HomeScreen(viewModel = homeViewModel)
        }

        screen(route = Route.Location) {
            LocationScreen(viewModel = locationViewModel)
        }

        screen(route = Route.Meal) {
            MealScreen(viewModel = mealViewModel)
        }

        screen(route = Route.ShoppingList) {
            ShoppingListScreen(viewModel = shoppingListViewModel)
        }

        screen(route = Route.AddFood) {
            AddFoodScreen(viewModel = addFoodViewModel)
        }

        screen(route = Route.AddShoppingItem) {
            AddShoppingItemScreen()
        }

        screen(route = Route.AddLocation) {
            AddLocationScreen()
        }
    }
}

private fun NavGraphBuilder.screen(
    route: br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route.value,
        enterTransition = {
            val previousRoute = br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.valueOf(initialState.destination.route)

            when (route.animation) {
                br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Animation.SlideVertical -> {
                    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up, tween(300))
                }

                br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Animation.SlideHorizontal -> {
                    if(previousRoute.animation == br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Animation.SlideVertical) {
                        fadeIn(initialAlpha = 1f)
                    } else {
                        val ordinal = previousRoute.ordinal
                        if (ordinal < route.ordinal) {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Left,
                                tween(300)
                            )
                        } else {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Right,
                                tween(300)
                            )
                        }
                    }
                }

                br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Animation.Fade -> {
                    fadeIn()
                }

                br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Animation.None -> {
                    null
                }
            }
        },
        exitTransition = {
            val nextRoute = br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.valueOf(targetState.destination.route)

            if(route.animation == br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Animation.SlideVertical) {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down, tween(300))
            } else {
                when (nextRoute.animation) {
                    br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Animation.SlideVertical -> {
                        null
                    }

                    br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Animation.SlideHorizontal -> {
                        val ordinal = nextRoute.ordinal
                        if (ordinal < route.ordinal) {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Right,
                                tween(300)
                            )
                        } else {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Left,
                                tween(300)
                            )
                        }
                    }

                    br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Animation.Fade -> {
                        fadeOut()
                    }

                    br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Animation.None -> {
                        null
                    }
                }
            }
        },
        popEnterTransition = {
            val nextRoute = br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.valueOf(targetState.destination.route)

            if(route.animation == br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Animation.SlideVertical) {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Down, tween(300))
            } else {
                when (nextRoute.animation) {
                    br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Animation.SlideVertical -> {
                        null
                    }

                    br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Animation.SlideHorizontal -> {
                        val ordinal = nextRoute.ordinal
                        if (ordinal < route.ordinal) {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Right,
                                tween(300)
                            )
                        } else {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Left,
                                tween(300)
                            )
                        }
                    }

                    br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Animation.Fade -> {
                        fadeIn()
                    }

                    br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Animation.None -> {
                        null
                    }
                }
            }
        },
        popExitTransition = {
            val previousRoute = br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.valueOf(initialState.destination.route)

            when (route.animation) {
                br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Animation.SlideVertical -> {
                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Up, tween(300))
                }

                br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Animation.SlideHorizontal -> {
                    if(previousRoute.animation == br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Animation.SlideVertical) {
                        fadeOut(targetAlpha = 1f)
                    } else {
                        val ordinal = previousRoute.ordinal
                        if (ordinal < route.ordinal) {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Left,
                                tween(300)
                            )
                        } else {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Right,
                                tween(300)
                            )
                        }
                    }
                }

                br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Animation.Fade -> {
                    fadeOut()
                }

                br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.Animation.None -> {
                    null
                }
            }
        },
        content = content
    )
}