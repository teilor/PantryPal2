package br.com.teilorsoares.pantrypal.presentation.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import br.com.teilorsoares.pantrypal.R
import br.com.teilorsoares.pantrypal.domain.model.Food

@Composable
fun Modifier.clickableWithoutRipple(
    onClick: () -> Unit,
    enabled: Boolean = true
) : Modifier {
    return this.clickable(
        onClick = onClick,
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        enabled = enabled
    )
}

@Composable
fun Food.Meal.getLabel() : String {
    return when(this) {
        Food.Meal.BREAKFAST -> stringResource(R.string.component_meal_breakfast)
        Food.Meal.LUNCH -> stringResource(R.string.component_meal_lunch)
        Food.Meal.SNACK -> stringResource(R.string.component_meal_snack)
        Food.Meal.DINNER -> stringResource(R.string.component_meal_dinner)
    }
}

@Composable
fun Food.Status.getBackgroundColor() : Color {
    return when(this) {
        Food.Status.EXPIRED -> Color(0xFFE64848)
        Food.Status.EXPIRING -> Color(0xFFF3B664)
        Food.Status.OK -> Color(0xFF9FBB73)
    }
}