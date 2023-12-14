package br.com.teilorsoares.pantrypal.presentation.screens.main.routes.addshoppingitem

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import br.com.teilorsoares.pantrypal.R
import br.com.teilorsoares.pantrypal.presentation.components.UITextField
import br.com.teilorsoares.pantrypal.presentation.components.UIToolbar
import br.com.teilorsoares.pantrypal.presentation.screens.main.navigation.MainNavigationEvent
import br.com.teilorsoares.pantrypal.presentation.theme.Brown1100
import br.com.teilorsoares.pantrypal.presentation.theme.Brown500
import org.koin.androidx.compose.getViewModel

@Composable
fun AddShoppingItemScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: AddShoppingItemViewModel = getViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.action.collect(::handleAction)
    }

    Box(
        contentAlignment = Alignment.TopCenter,
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
                    title = stringResource(R.string.screen_add_shopping_item_title),
                    modifier = Modifier
                        .padding(top = 16.dp, start = 12.dp)
                )
            }

            item {
                UITextField(
                    value = state.shoppingItemName,
                    onValueChange = { viewModel.changedShoppingName(it) },
                    label = "Item",
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .padding(horizontal = 24.dp)
                )
            }
        }


        Box(
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.BottomEnd)
                .size(58.dp)
                .clip(CircleShape)
                .background(color = Brown1100)
                .clickable(
                    enabled = state.shoppingItemName.isNotBlank()
                ) { viewModel.clickedSaveShoppingItem() }
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
    }
}

private fun handleAction(action: AddShoppingItemUiAction) {
    when (action) {
        AddShoppingItemUiAction.FinishScreen -> {
            MainNavigationEvent.navigateBack()
        }
    }
}