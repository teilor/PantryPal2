package br.com.teilorsoares.pantrypal.presentation.screens.main.routes.addfood

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.teilorsoares.pantrypal.R
import br.com.teilorsoares.pantrypal.domain.model.Food
import br.com.teilorsoares.pantrypal.presentation.components.UIAsyncImage
import br.com.teilorsoares.pantrypal.presentation.theme.Brown1300
import br.com.teilorsoares.pantrypal.presentation.theme.Brown500
import br.com.teilorsoares.pantrypal.presentation.theme.Brown700
import br.com.teilorsoares.pantrypal.presentation.util.getBackgroundColor
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UIFoodItem(
    food: Food,
    onQuantityClick: () -> Unit,
    onDiscardClick: () -> Unit,
    modifier: Modifier = Modifier,
    showExpiryDate: Boolean = true
) {
    AnimatedVisibility(
        visible = food.visible,
        modifier = modifier
    ) {
        Box {
            var expanded by remember { mutableStateOf(false) }

            if(showExpiryDate) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(horizontal = 16.dp)
                        .padding(top = 12.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(food.status.getBackgroundColor())
                        .padding(horizontal = 4.dp)
                ) {
                    Text(
                        text = remember(food) {
                            val day = SimpleDateFormat("dd").format(food.expiryDate)
                            val month = SimpleDateFormat("MMM").format(food.expiryDate).capitalize(Locale.getDefault())
                            "$day $month"
                        },
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight(400),
                            color = Color.White,
                            letterSpacing = 0.25.sp,
                        )
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 12.dp)
                    .fillMaxWidth()
                    .height(80.dp)
                    .border(
                        width = 1.dp,
                        brush = SolidColor(Brown500),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .clip(RoundedCornerShape(4.dp))
                    .combinedClickable(
                        onClick = { if (!food.consumedOrDiscarded) onQuantityClick() },
                        onLongClick = { expanded = true }
                    )
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(top = 16.dp, start = 16.dp, bottom = 16.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(food.status.getBackgroundColor())
                ) {
                    Text(
                        text = food.name.first().uppercase(),
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 12.sp,
                            fontWeight = FontWeight(400),
                            color = Brown1300,
                        )
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = food.name,
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight(500),
                            color = Brown500,
                            letterSpacing = 0.15.sp,
                        ),
                        maxLines = 1,
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .height(24.dp)
                    )

                    Text(
                        text = if (food.quantity > 0)
                            stringResource(
                                R.string.component_food_item_quantity,
                                food.quantity,
                                if (food.quantity > 1) "s" else ""
                            )
                        else
                            stringResource(R.string.component_food_item_quantity_empty),
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight(400),
                            color = Brown700,
                            letterSpacing = 0.25.sp,
                        )
                    )
                }

                Box {
                    UIAsyncImage(
                        model = Uri.parse(food.photo),
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp))
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(color = Brown1300)
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                expanded = false
                                onDiscardClick()
                            },
                            text = {
                                Text(
                                    text = stringResource(R.string.screen_home_food_option_discard),
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
}