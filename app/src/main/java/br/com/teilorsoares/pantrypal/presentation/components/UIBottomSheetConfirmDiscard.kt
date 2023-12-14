package br.com.teilorsoares.pantrypal.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.teilorsoares.pantrypal.R
import br.com.teilorsoares.pantrypal.domain.model.Food
import br.com.teilorsoares.pantrypal.presentation.theme.Brown300

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UIBottomSheetConfirmDiscard(
    food: Food?,
    onAddToShoppingItemClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                text = food?.name.orEmpty(),
                style = TextStyle(
                    fontSize = 24.sp,
                    lineHeight = 26.sp,
                    fontWeight = FontWeight(400),
                    color = Brown300,
                    letterSpacing = 0.5.sp
                ),
                modifier = Modifier.padding(top = 16.dp)
            )

            Text(
                text = stringResource(R.string.component_bottom_sheet_discard_food_item_message),
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight(400),
                    color = Brown300,
                    letterSpacing = 0.5.sp,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(top = 16.dp)
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth()
            ) {
                OutlinedButton(onClick = onDeleteClick) {
                    Text(text = stringResource(R.string.component_bottom_sheet_discard_food_item_remove_btn))
                }

                Button(onClick = onAddToShoppingItemClick) {
                    Text(text = stringResource(R.string.component_bottom_sheet_discard_food_item_add_shopping_list_btn))
                }
            }
        }
    }
}