package br.com.teilorsoares.pantrypal.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.teilorsoares.pantrypal.R
import br.com.teilorsoares.pantrypal.presentation.theme.Brown1400
import br.com.teilorsoares.pantrypal.presentation.theme.Brown300
import br.com.teilorsoares.pantrypal.presentation.theme.Brown600
import br.com.teilorsoares.pantrypal.presentation.util.clickableWithoutRipple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UIBottomSheetQuantity(
    quantity: String,
    onQuantityChange: (String) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    var quantityState by remember { mutableStateOf(quantity) }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .navigationBarsPadding()
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.screen_home_quantity_bottom_sheet_title),
                style = TextStyle(
                    fontSize = 24.sp,
                    lineHeight = 26.sp,
                    fontWeight = FontWeight(400),
                    color = Brown300,
                    letterSpacing = 0.5.sp
                ),
                modifier = Modifier.padding(top = 16.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(top = 12.dp, bottom = 16.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Brown600,
                            shape = RoundedCornerShape(size = 4.dp)
                        )
                        .size(30.dp)
                        .clip(RoundedCornerShape(size = 4.dp))
                        .clickableWithoutRipple(
                            onClick = {
                                quantityState = (quantityState.toInt() - 1).toString()
                                onQuantityChange(quantityState)
                            }
                        )
                        .background(color = Brown1400)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_quantity_remove),
                        contentDescription = stringResource(R.string.screen_home_bottom_sheet_quantity_remove),
                        modifier = Modifier.size(24.dp)
                    )
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Brown600,
                            shape = RoundedCornerShape(size = 4.dp)
                        )
                        .width(133.dp)
                        .background(color = Brown1400, shape = RoundedCornerShape(size = 4.dp))
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = quantityState,
                        style = TextStyle(
                            fontSize = 48.sp,
                            lineHeight = 50.sp,
                            fontWeight = FontWeight(400),
                            color = Brown300,
                            letterSpacing = 0.5.sp
                        )
                    )
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Brown600,
                            shape = RoundedCornerShape(size = 4.dp)
                        )
                        .size(30.dp)
                        .clip(RoundedCornerShape(size = 4.dp))
                        .clickableWithoutRipple(
                            onClick = {
                                quantityState = (quantityState.toInt() + 1).toString()
                                onQuantityChange(quantityState)
                            }
                        )
                        .background(color = Brown1400)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_quantity_add),
                        contentDescription = stringResource(R.string.screen_home_bottom_sheet_quantity_add),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}