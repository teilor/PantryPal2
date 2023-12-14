package br.com.teilorsoares.pantrypal.presentation.screens.main

import android.Manifest
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.teilorsoares.pantrypal.R
import br.com.teilorsoares.pantrypal.presentation.screens.main.navigation.MainBottomNavigation
import br.com.teilorsoares.pantrypal.presentation.screens.main.navigation.MainMenuNavigation
import br.com.teilorsoares.pantrypal.presentation.screens.main.navigation.MainNavigation
import br.com.teilorsoares.pantrypal.presentation.screens.main.routes.addfood.AddFoodViewModel
import br.com.teilorsoares.pantrypal.presentation.screens.main.routes.home.HomeViewModel
import br.com.teilorsoares.pantrypal.presentation.screens.main.routes.location.LocationViewModel
import br.com.teilorsoares.pantrypal.presentation.screens.main.routes.meal.MealViewModel
import br.com.teilorsoares.pantrypal.presentation.screens.main.routes.shoppinglist.ShoppingListViewModel
import br.com.teilorsoares.pantrypal.presentation.theme.Brown1100
import br.com.teilorsoares.pantrypal.presentation.theme.Brown500
import br.com.teilorsoares.pantrypal.presentation.theme.PantryPalTheme
import br.com.teilorsoares.pantrypal.presentation.util.PictureEvent
import br.com.teilorsoares.pantrypal.presentation.util.RemindersManager
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    internal val viewModel: MainViewModel by viewModel()

    internal val homeViewModel: HomeViewModel by viewModel()
    internal val locationViewModel: LocationViewModel by viewModel()
    internal val mealViewModel: MealViewModel by viewModel()
    internal val shoppingListViewModel: ShoppingListViewModel by viewModel()

    internal val addFoodViewModel: AddFoodViewModel by viewModel()

    internal lateinit var navController: NavHostController

    internal val takeImageResult = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess) {
            latestUri?.let { uri ->
                PictureEvent.selectPicture(PictureEvent.PictureSource.CAMERA, uri)
            }
        }
    }

    internal val selectImageFromGalleryResult = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            PictureEvent.selectPicture(PictureEvent.PictureSource.GALLERY, it)
        }
    }

    internal var latestUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PantryPalTheme {
                navController = rememberNavController()
                Content()
                ObserveActions()
                ObserveBackPress()
            }
        }

        requestNeedPermissions()
    }

    @Composable
    private fun Content() {
        val state by viewModel.state.collectAsStateWithLifecycle()

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AnimatedVisibility(
                visible = state.isMenuVisible,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                MainMenuNavigation(
                    toolbar = state.selectedTab.toolbar
                )
            }

            Box(
                modifier = Modifier.weight(1f)
            ) {
                MainNavigation(
                    navController = navController
                )

                this@Column.AnimatedVisibility(
                    visible = state.isAddVisible,
                    enter = slideInHorizontally { it },
                    exit = slideOutHorizontally { it },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(12.dp)
                            .size(58.dp)
                            .clip(CircleShape)
                            .background(color = Brown1100)
                            .clickable { viewModel.clickedAdd() }
                            .padding(11.dp)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_add),
                            contentDescription = stringResource(R.string.screen_home_btn_add),
                            tint = Brown500,
                            modifier = Modifier
                                .padding(1.dp)
                                .size(36.dp)
                        )
                    }
                }
            }

            AnimatedVisibility(
                visible = state.isBottomNavigationVisible,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                MainBottomNavigation(
                    selectedTab = state.selectedTab,
                    tabs = state.tabs,
                    onTabClick = viewModel::clickedTab,
                )
            }
        }
    }

    @Composable
    private fun ObserveActions() {
        LaunchedEffect(Unit) {
            viewModel.action.collect(::handleAction)
        }
    }

    @Composable
    private fun ObserveBackPress() {
        BackHandler {
            viewModel.clickedBack()
        }
    }

    // Daily Notifications
    private fun requestNeedPermissions() {
        val permissions: MutableList<String> = mutableListOf()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions.add(Manifest.permission.POST_NOTIFICATIONS)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permissions.add(Manifest.permission.SCHEDULE_EXACT_ALARM)
        }

        requestPermissions(permissions.toTypedArray()) { isGranted ->
            if (isGranted) {
                initializeDailyNotification()
            }
        }
    }

    private fun requestPermissions(permissions: Array<String>, callback: (Boolean) -> Unit) {
        val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result: Map<String, Boolean> ->
            callback(result.all { it.value })
        }
        permissionLauncher.launch(permissions)
    }

    private fun initializeDailyNotification() {
        RemindersManager.startReminder(this)
    }
}
