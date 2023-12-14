package br.com.teilorsoares.pantrypal.presentation.screens.main.routes.shoppinglist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.teilorsoares.pantrypal.R
import br.com.teilorsoares.pantrypal.domain.model.ShoppingItem
import br.com.teilorsoares.pantrypal.presentation.theme.Brown1300
import br.com.teilorsoares.pantrypal.presentation.theme.Brown500
import br.com.teilorsoares.pantrypal.presentation.theme.Brown800
import br.com.teilorsoares.pantrypal.presentation.util.clickableWithoutRipple
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShoppingListScreen(
    modifier: Modifier = Modifier,
    viewModel: ShoppingListViewModel = getViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(top = 8.dp)
        ) {
            itemsIndexed(
                items = state.shoppingItems,
                key = { _, item -> item.id }
            ) { index, item ->
                ShoppingItem(
                    item = item,
                    onClick = { viewModel.clickedShoppingItem(index, item) },
                    onDeleteClick = { viewModel.clickedDeleteShoppingItem(index, item) }
                )
            }
        }
    }
}

@Composable
private fun ShoppingItem(
    item: ShoppingItem,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = item.visible,
        enter = expandVertically(),
        exit = shrinkVertically(tween(200)),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .clickableWithoutRipple(onClick = onClick)
                .padding(start = 16.dp, end = 24.dp)
                .padding(vertical = 4.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(40.dp)
                    .background(color = Brown800, shape = CircleShape),
            ) {

                Text(
                    text = item.name.first().uppercase(),
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight(500),
                        color = Brown500,
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.15.sp
                    )
                )
            }

            Text(
                text = item.name,
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight(400),
                    color = Brown500,
                    letterSpacing = 0.5.sp,
                ),
                modifier = Modifier.weight(1f)
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .width(44.dp)
                    .height(48.dp)
                    .padding(top = 4.dp, end = 4.dp, bottom = 4.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_unchecked),
                    contentDescription = stringResource(
                        if (item.purchased)
                            R.string.screen_shopping_list_item_click_from_purchased
                        else
                            R.string.screen_shopping_list_item_click_to_buy
                    ),
                    tint = Brown500,
                    modifier = Modifier
                        .padding(11.dp)
                        .background(
                            color = if (item.purchased) Brown500 else Color.White,
                            shape = RoundedCornerShape(2.dp)
                        )
                )

                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_check_small),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(24.dp)
                )

                var showOptions by remember { mutableStateOf(false) }
                DropdownMenu(
                    expanded = showOptions,
                    onDismissRequest = { showOptions = false },
                    modifier = Modifier.background(color = Brown1300)
                ) {
                    DropdownMenuItem(
                        onClick = {
                            showOptions = false
                            onDeleteClick()
                        },
                        text = {
                            Text(
                                text = stringResource(R.string.screen_shopping_list_item_option_delete),
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    lineHeight = 24.sp,
                                    fontWeight = FontWeight(400),
                                    color = Brown500,
                                    letterSpacing = 0.5.sp
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}