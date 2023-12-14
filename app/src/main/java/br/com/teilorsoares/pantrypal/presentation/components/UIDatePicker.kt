package br.com.teilorsoares.pantrypal.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
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
import br.com.teilorsoares.pantrypal.presentation.theme.Brown1300
import br.com.teilorsoares.pantrypal.presentation.theme.Brown300
import br.com.teilorsoares.pantrypal.presentation.theme.Brown350
import br.com.teilorsoares.pantrypal.presentation.theme.Brown500
import logcat.logcat
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "UIDatePicker"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UIDatePicker(
    label: String,
    onSelectedDate: (Long?) -> Unit,
    modifier: Modifier = Modifier,
    state: DatePickerState = rememberDatePickerState()
) {
    var openDialog by remember { mutableStateOf(false) }

    val placeholder = stringResource(id = R.string.component_date_picker_placeholder)

    val selectedDate = state.selectedDateMillis
    val formattedDate = remember(selectedDate) {
        if (selectedDate != null) {
            logcat (TAG) { "selectedDate: $selectedDate" }
            SimpleDateFormat(
                "dd/MM/yyyy",
                Locale("pt", "BR")
                // plus 12h to fix the timezone
            ).format(selectedDate + 12 * 60 * 60 * 1000)
        } else {
            placeholder
        }
    }

    Column(
        modifier = modifier
    ) {
        Text(
            text = label,
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight(400),
                color = Brown350
            ),
            modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Brown500,
                    shape = RoundedCornerShape(4.dp)
                )
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .clickable { openDialog = true }
                .background(color = Brown1300)
                .padding(start = 12.dp)
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = formattedDate,
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight(400),
                    color = Brown300,
                ),
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_calendar),
                contentDescription = null,
                tint = Brown500,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(24.dp)
            )
        }
    }

    if (openDialog) {
        DatePickerDialog(
            onDismissRequest = { openDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                        onSelectedDate(state.selectedDateMillis)
                    }
                ) {
                    Text("OK")
                }
            },
        ) {
            DatePicker(state = state)
        }
    }
}