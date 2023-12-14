package br.com.teilorsoares.pantrypal.presentation.screens.main.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.teilorsoares.pantrypal.presentation.theme.Brown100
import br.com.teilorsoares.pantrypal.presentation.theme.Brown300
import br.com.teilorsoares.pantrypal.presentation.theme.Brown900
import br.com.teilorsoares.pantrypal.presentation.util.clickableWithoutRipple

@Composable
fun MainBottomNavigation(
    selectedTab: MainTab,
    tabs: List<MainTab>,
    onTabClick: (MainTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .height(60.dp)
            .background(Brown900)
            .padding(
                horizontal = 16.dp,
                vertical = 10.dp
            )
    ) {
        tabs.forEach { tab ->
            MainBottomNavigationItem(
                icon = ImageVector.vectorResource(tab.icon),
                title = stringResource(tab.title),
                isSelected = tab == selectedTab,
                onClick = { onTabClick(tab) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun MainBottomNavigationItem(
    icon: ImageVector,
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clickableWithoutRipple(onClick = onClick)

    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = if (isSelected) Brown100 else Brown300,
            modifier = Modifier
                .widthIn(max = 32.dp)
                .weight(1f)
                .aspectRatio(1f)
                .padding(1.dp)
        )

        AnimatedVisibility(
            visible = isSelected,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight(700),
                    color = Brown100,
                    textAlign = TextAlign.Center,
                )
            )
        }
    }
}