package br.com.teilorsoares.pantrypal.presentation.screens.main.routes.addfood

import android.net.Uri
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import br.com.teilorsoares.pantrypal.domain.model.Food
import br.com.teilorsoares.pantrypal.presentation.arch.state.UiState
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
data class AddFoodUiState constructor(
    val name: String = "",
    val photo: Uri? = null,
    val showTakePhotoBottomSheet: Boolean = false,
    val expirationDate: Long? = null,
    val quantity: String = "",
    val location: String? = null,
    val locations: List<String> = listOf("Adicionar"),
    val meal: Food.Meal? = null,
    val meals: List<Food.Meal> = Food.Meal.values().toList(),
    val datePickerState: DatePickerState = DatePickerState(locale = Locale("pt", "BR")),
) : UiState
