package br.com.teilorsoares.pantrypal.presentation.screens.main.routes.addfood

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.teilorsoares.pantrypal.R
import br.com.teilorsoares.pantrypal.presentation.components.UIAsyncImage
import br.com.teilorsoares.pantrypal.presentation.components.UIDatePicker
import br.com.teilorsoares.pantrypal.presentation.components.UIDropdown
import br.com.teilorsoares.pantrypal.presentation.components.UILifecycleEvents
import br.com.teilorsoares.pantrypal.presentation.components.UITextField
import br.com.teilorsoares.pantrypal.presentation.components.UIToolbar
import br.com.teilorsoares.pantrypal.presentation.screens.main.MainActivity
import br.com.teilorsoares.pantrypal.presentation.screens.main.navigation.MainNavigationEvent
import br.com.teilorsoares.pantrypal.presentation.theme.Brown1100
import br.com.teilorsoares.pantrypal.presentation.theme.Brown1300
import br.com.teilorsoares.pantrypal.presentation.theme.Brown300
import br.com.teilorsoares.pantrypal.presentation.theme.Brown500
import br.com.teilorsoares.pantrypal.presentation.theme.Brown600
import br.com.teilorsoares.pantrypal.presentation.util.PictureEvent
import br.com.teilorsoares.pantrypal.presentation.util.clickableWithoutRipple
import br.com.teilorsoares.pantrypal.presentation.util.getLabel
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainActivity.AddFoodScreen(
    modifier: Modifier = Modifier,
    viewModel: AddFoodViewModel = getViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val mealsOptions = state.meals.map { it.getLabel() }

    LaunchedEffect(Unit) {
        viewModel.action.collect(::handleAction)
    }

    UILifecycleEvents(
        onStart = { viewModel.started() }
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            item {
                UIToolbar(
                    onBackClick = viewModel::clickedBack,
                    title = stringResource(R.string.screen_add_food_title),
                    modifier = Modifier
                        .padding(top = 16.dp, start = 12.dp)
                )
            }

            item {
                TakePhoto(
                    photo = state.photo,
                    onTakePhoto = { viewModel.clickedTakePhoto() },
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }

            item {
                UITextField(
                    value = state.name,
                    onValueChange = { viewModel.changedName(it) },
                    label = stringResource(R.string.screen_add_food_label_name),
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .padding(horizontal = 24.dp)
                )
            }

            item {
                UIDatePicker(
                    state = state.datePickerState,
                    label = stringResource(R.string.screen_add_food_label_validate),
                    onSelectedDate = { viewModel.changedExpirationDate(it) },
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .padding(horizontal = 24.dp)
                )
            }

            item {
                UITextField(
                    value = state.quantity,
                    onValueChange = { viewModel.changedQuantity(it) },
                    label = stringResource(R.string.screen_add_food_label_quantity),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .padding(horizontal = 24.dp)
                )
            }

            item {
                UIDropdown(
                    label = stringResource(R.string.screen_add_food_location_dropdown_label),
                    placeholder = stringResource(R.string.screen_add_food_location_dropdown_placeholder),
                    selected = state.location,
                    options = state.locations,
                    onSelected = { viewModel.changedLocation(it) },
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .padding(horizontal = 24.dp)
                )
            }

            item {
                UIDropdown(
                    label = stringResource(R.string.screen_add_food_meal_dropdown_labe),
                    placeholder = stringResource(R.string.screen_add_food_meal_dropdown_placeholder),
                    selected = state.meal?.getLabel(),
                    options = mealsOptions,
                    onSelected = {
                        viewModel.changedMeal(state.meals[mealsOptions.indexOf(it)])
                    },
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .padding(horizontal = 24.dp)
                )
            }

            item {
                Spacer(modifier = Modifier.height(78.dp))
            }
        }

        Box(
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.BottomEnd)
                .size(58.dp)
                .clip(CircleShape)
                .background(color = Brown1100)
                .clickable { viewModel.clickedSave(this@AddFoodScreen) }
                .padding(11.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_done),
                contentDescription = stringResource(R.string.screen_add_food_btn_done),
                tint = Brown500,
                modifier = Modifier
                    .padding(1.dp)
                    .size(36.dp)
            )
        }

        if (state.showTakePhotoBottomSheet) {
            BottomSheetTakePhoto(
                onDismissRequest = { viewModel.clickedDismissTakePhotoBottomSheet() },
                onCameraClick = { viewModel.clickedTakePhotoWithCamera() },
                onGalleryClick = { viewModel.clickedTakePhotoWithGallery() },
            )
        }
    }

    BackHandler {
        viewModel.clickedBack()
    }
}

// Components
@Composable
private fun TakePhoto(
    photo: Uri?,
    onTakePhoto: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(top = 24.dp)
            .border(
                width = 1.dp,
                color = Brown500,
                shape = RoundedCornerShape(size = 4.dp)
            )
            .size(200.dp)
            .background(color = Brown1300, shape = RoundedCornerShape(size = 4.dp))
            .clickableWithoutRipple(
                onClick = onTakePhoto
            )
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_add_photo),
            contentDescription = stringResource(R.string.screen_add_food_btn_add_photo),
            tint = Brown500,
            modifier = Modifier.size(85.dp)
        )

        photo?.let { uri ->
            UIAsyncImage(
                model = uri,
                contentDescription = stringResource(R.string.screen_add_food_btn_add_photo),
                contentScale = ContentScale.FillWidth,
                size = 600,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(size = 4.dp))
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheetTakePhoto(
    onDismissRequest: () -> Unit,
    onCameraClick: () -> Unit,
    onGalleryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(horizontal = 24.dp)
        ) {
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .clickableWithoutRipple(onClick = onCameraClick)
            ) {
                Text(
                    text = stringResource(R.string.screen_add_food_bottom_sheet_item_camera),
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight(400),
                        color = Brown300,
                    )
                )
            }
            HorizontalDivider(color = Brown600)
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .clickableWithoutRipple(onClick = onGalleryClick)
            ) {
                Text(
                    text = stringResource(R.string.screen_add_food_bottom_sheet_item_gallery),
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight(400),
                        color = Brown300,
                    )
                )
            }
            HorizontalDivider(color = Brown600)
        }
    }
}

// Handle Actions
private fun MainActivity.handleAction(action: AddFoodUiAction) {
    when (action) {
        is AddFoodUiAction.TakePhoto -> {
            PictureEvent.takePicture(action.source)
        }
        is AddFoodUiAction.NavigateToAddLocationScreen -> {
            MainNavigationEvent.navigateTo(br.com.teilorsoares.pantrypal.presentation.screens.main.routes.Route.AddLocation)
        }
        is AddFoodUiAction.NavigateToBack -> {
            MainNavigationEvent.navigateBack()
        }
        is AddFoodUiAction.ShowError -> {
            Toast.makeText(
                this,
                "Ocorreu um erro ao adicionar o alimento",
                Toast.LENGTH_SHORT
            ).show()
        }
        is AddFoodUiAction.MissingFields -> {
            Toast.makeText(
                this,
                "É necessário preencher todos os campos!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}