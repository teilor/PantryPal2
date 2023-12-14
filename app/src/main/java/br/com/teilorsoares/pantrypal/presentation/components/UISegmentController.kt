package br.com.teilorsoares.pantrypal.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import br.com.teilorsoares.pantrypal.presentation.theme.Brown1100
import br.com.teilorsoares.pantrypal.presentation.theme.Brown1200
import br.com.teilorsoares.pantrypal.presentation.theme.Brown500
import br.com.teilorsoares.pantrypal.presentation.theme.Brown600

@Composable
fun UISegmentController(
    selectedItemIndex: Int,
    items: List<String>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    TabRow(
        selectedTabIndex = selectedItemIndex,
        contentColor = Brown500,
        containerColor = Brown1100,
        indicator = {
            Box(
                Modifier
                    .tabIndicatorOffset(it[selectedItemIndex])
                    .zIndex(-3f)
                    .height(24.dp)
                    .padding(horizontal = 4.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Brown500)
            )
        },
        divider = {},
        modifier = modifier
            .fillMaxWidth()
            .height(32.dp)
            .clip(RoundedCornerShape(4.dp)),
    ) {
        items.mapIndexed { i, item ->
            Tab(
                selected = selectedItemIndex == i,
                onClick = { onItemClick(i) }
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .weight(1f)
                        .height(24.dp)
                ) {
                    Text(
                        text = item,
                        fontSize = 14.sp,
                        lineHeight = 16.sp,
                        fontWeight = FontWeight(400),
                        color = if (i == selectedItemIndex) Brown1200 else Brown600,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}