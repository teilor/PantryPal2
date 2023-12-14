package br.com.teilorsoares.pantrypal.presentation.screens.main.routes.location

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.teilorsoares.pantrypal.presentation.components.UIBottomSheetConfirmDiscard
import br.com.teilorsoares.pantrypal.presentation.components.UIBottomSheetQuantity
import br.com.teilorsoares.pantrypal.presentation.screens.main.routes.addfood.UIFoodItem
import br.com.teilorsoares.pantrypal.presentation.theme.Brown1100
import br.com.teilorsoares.pantrypal.presentation.theme.Brown200
import org.koin.androidx.compose.getViewModel
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LocationScreen(
    modifier: Modifier = Modifier,
    viewModel: LocationViewModel = getViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn {
            state.groups.forEachIndexed { index, group ->
                item {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(top = if(index == 0) 0.dp else 16.dp, bottom = 4.dp)
                            .fillMaxWidth()
                            .background(color = Brown1100)
                            .padding(top = 6.dp, bottom = 6.dp)
                    ) {
                        Text(
                            text = group.title,
                            style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 16.sp,
                                fontWeight = FontWeight(400),
                                color = Brown200,
                            )
                        )
                    }
                }

                items(
                    items = group.foods,
                    key = { it.id }
                ) { food ->
                    UIFoodItem(
                        food = food,
                        onDiscardClick = { viewModel.clickedDiscard(food) },
                        onQuantityClick = { viewModel.clickedQuantity(food) },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(72.dp)) }
        }
    }

    if (state.expandedQuantity) {
        UIBottomSheetQuantity(
            quantity = state.selectedFood?.quantity.toString(),
            onQuantityChange = { viewModel.clickedQuantityChange(it) },
            onDismissRequest = { viewModel.clickedQuantityDismiss() }
        )
    }

    if(state.expandedConsumedFood) {
        UIBottomSheetConfirmDiscard(
            food = state.selectedFood,
            onAddToShoppingItemClick = { viewModel.clickedAddShoppingItemConfirm() },
            onDeleteClick = { viewModel.clickedDeleteConfirm() },
            onDismissRequest = { viewModel.clickedDiscardDismiss() }
        )
    }
}

