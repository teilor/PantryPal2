package br.com.teilorsoares.pantrypal.presentation.screens.main.routes.addshoppingitem

import androidx.lifecycle.viewModelScope
import br.com.teilorsoares.pantrypal.domain.model.ShoppingItem
import br.com.teilorsoares.pantrypal.domain.usecase.shippingitem.InsertShoppingItemUseCase
import br.com.teilorsoares.pantrypal.presentation.arch.ViewModel
import br.com.teilorsoares.pantrypal.presentation.screens.main.navigation.MainNavigationEvent
import kotlinx.coroutines.launch
import java.util.*

class AddShoppingItemViewModel(
    private val insertShoppingItemUseCase: InsertShoppingItemUseCase
) : ViewModel<AddShoppingItemUiState, AddShoppingItemUiAction>(AddShoppingItemUiState()) {

    fun changedShoppingName(shoppingItemName: String) {
        viewModelScope.launch {
            setState { state -> state.copy(shoppingItemName = shoppingItemName) }
        }
    }

    fun clickedSaveShoppingItem() {
        viewModelScope.launch {
            val shoppingItemName = state.value.shoppingItemName
            if (shoppingItemName.isNotBlank()) {
                insertShoppingItemUseCase(
                    ShoppingItem(
                        id = UUID.randomUUID().toString(),
                        name = shoppingItemName,
                        purchased = false
                    )
                )

                sendAction { AddShoppingItemUiAction.FinishScreen }
            }
        }
    }

    fun clickedBack() {
        MainNavigationEvent.navigateBack()
    }
}