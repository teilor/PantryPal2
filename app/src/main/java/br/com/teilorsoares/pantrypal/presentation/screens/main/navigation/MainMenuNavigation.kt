package br.com.teilorsoares.pantrypal.presentation.screens.main.navigation

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.teilorsoares.pantrypal.presentation.screens.main.MainActivity
import br.com.teilorsoares.pantrypal.presentation.theme.Brown200
import br.com.teilorsoares.pantrypal.presentation.theme.Brown500
import br.com.teilorsoares.pantrypal.presentation.theme.Brown900


@Composable
internal fun MainActivity.MainMenuNavigation(
    @StringRes
    toolbar: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Brown900)
    ) {
        Text(
            text = stringResource(toolbar),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = Brown200,
                textAlign = TextAlign.Start
            ),
            modifier = Modifier
                .align(Alignment.Center)
        )

        var expandedMenu by remember { mutableStateOf(false) }
        IconButton(
            onClick = { expandedMenu = true },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .clip(CircleShape)
        ) {
            Icon(
                imageVector = Icons.Rounded.MoreVert,
                contentDescription = null,
                tint = Brown500,
                modifier = Modifier.size(24.dp)
            )

            DropdownMenu(
                expanded = expandedMenu,
                onDismissRequest = { expandedMenu = false }
            ) {
                DropdownMenuItem(
                    onClick = {
                        expandedMenu = false
                        viewModel.clickedNavigationActivity(MainNavigationEvent.Activities.Activity01)
                    },
                    text = {
                        Text(
                            text = "Relatórios",
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

                DropdownMenuItem(
                    onClick = {
                        expandedMenu = false
                        viewModel.clickedNavigationActivity(MainNavigationEvent.Activities.Activity02)
                    },
                    text = {
                        Text(
                            text = "Configurações",
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