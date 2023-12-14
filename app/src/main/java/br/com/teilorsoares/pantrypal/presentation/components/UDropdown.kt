package br.com.teilorsoares.pantrypal.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.teilorsoares.pantrypal.R
import br.com.teilorsoares.pantrypal.presentation.theme.Brown1000
import br.com.teilorsoares.pantrypal.presentation.theme.Brown1300
import br.com.teilorsoares.pantrypal.presentation.theme.Brown300
import br.com.teilorsoares.pantrypal.presentation.theme.Brown350
import br.com.teilorsoares.pantrypal.presentation.theme.Brown500

@Composable
fun UIDropdown(
    label: String,
    placeholder: String,
    selected: String?,
    options: List<String>,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val shape = RoundedCornerShape(4.dp)

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
                    shape = shape
                )
                .fillMaxWidth()
                .clip(shape)
                .clickable { expanded = true }
                .background(color = Brown1300)
                .padding(start = 12.dp)
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = selected ?: placeholder,
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight(400),
                    color = Brown300,
                ),
                modifier = Modifier.weight(1f)
            )

            Box {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        if(expanded) R.drawable.ic_arrow_up
                         else R.drawable.ic_arrow_down
                    ),
                    contentDescription = null,
                    tint = Brown500,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(24.dp)
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(color = Brown1300)
                ) {
                    options.forEachIndexed { index, value ->
                        DropdownMenuItem(
                            onClick = {
                                expanded = false
                                onSelected(value)
                            },
                            text = {
                                Text(
                                    text = value,
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        lineHeight = 24.sp,
                                        fontWeight = FontWeight(400),
                                        color = Brown500,
                                        letterSpacing = 0.5.sp
                                    )
                                )
                            },
                            modifier = Modifier.background(
                                color = if (index == options.indexOf(selected)) Brown1000 else Brown1300
                            )
                        )
                    }
                }
            }
        }

        /*AnimatedVisibility(
            visible = expanded,
            enter = expandVertically(expandFrom = Alignment.Top),
            exit = shrinkVertically(shrinkTowards = Alignment.Top)
        ) {
            Column(
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                options.forEachIndexed { index, value ->
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onSelected(value)
                        },
                        text = {
                            Text(
                                text = value,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    lineHeight = 24.sp,
                                    fontWeight = FontWeight(400),
                                    color = Brown500,
                                    letterSpacing = 0.5.sp
                                )
                            )
                        },
                        modifier = Modifier
                            .width(200.dp)
                            .height(40.dp)
                            .background(color = if (index == options.indexOf(selected)) Brown1000 else Brown1300)
                    )
                }
            }
        }*/
    }
}