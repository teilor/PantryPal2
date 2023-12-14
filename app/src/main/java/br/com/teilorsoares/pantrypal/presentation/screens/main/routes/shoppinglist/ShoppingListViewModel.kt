package br.com.teilorsoares.pantrypal.presentation.screens.main.routes.shoppinglist

import androidx.lifecycle.viewModelScope
import br.com.teilorsoares.pantrypal.domain.model.ShoppingItem
import br.com.teilorsoares.pantrypal.domain.usecase.shippingitem.DeleteShoppingItemUseCase
import br.com.teilorsoares.pantrypal.domain.usecase.shippingitem.GetShoppingItemUseCase
import br.com.teilorsoares.pantrypal.domain.usecase.shippingitem.UpdateShoppingItemPurchasedUseCase
import br.com.teilorsoares.pantrypal.presentation.arch.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import logcat.asLog
import logcat.logcat

private const val TAG = "ShoppingListViewModel"

class ShoppingListViewModel(
    private val getShoppingItemUseCase: GetShoppingItemUseCase,
    private val updateShoppingItemPurchasedUseCase: UpdateShoppingItemPurchasedUseCase,
    private val deleteShoppingItemUseCase: DeleteShoppingItemUseCase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel<ShoppingListUiState, ShoppingListUiAction>(ShoppingListUiState()) {
    private var collectShoppingList: Job? = null

    init {
        collectShoppingList(state.value.purchased)
    }

    fun clickedTab(index: Int) {
        viewModelScope.launch {
            val purchased = index == 1
            setState { state ->
                state.copy(
                    selectedTabIndex = index,
                    purchased = purchased
                )
            }

            collectShoppingList(purchased)
        }
    }

    private fun collectShoppingList(purchased: Boolean) {
        collectShoppingList?.cancel()
        collectShoppingList = viewModelScope.launch {
            setState { state -> state.copy(purchased = purchased) }
            getShoppingItemUseCase(purchased)
                .flowOn(ioDispatcher)
                .collect { data ->
                    setState { state -> state.copy(shoppingItems = data) }
                }
        }
    }

    fun clickedShoppingItem(index: Int, item: ShoppingItem) {
        viewModelScope.launch {
            try {
                updateItemVisibility(index, item)
                with(ioDispatcher) { delay(400) }
                updateShoppingItemPurchasedUseCase(item.id, !item.purchased)
            } catch (e: Exception) {
                logcat(TAG) { e.asLog() }
            }
        }
    }

    fun clickedDeleteShoppingItem(index: Int, item: ShoppingItem) {
        viewModelScope.launch {
            try {
                updateItemVisibility(index, item)
                withContext(ioDispatcher) { delay(300) }
                deleteShoppingItemUseCase(item.id)
            } catch (e: Exception) {
                logcat(TAG) { e.asLog() }
            }
        }
    }

    // Method for updating the visibility of the item in the list
    // Responsible for the animation of the item disappearing
    private suspend fun updateItemVisibility(index: Int, item: ShoppingItem) {
        try {
            setState { state ->
                val shoppingItems = state.shoppingItems.toMutableList()
                shoppingItems[index] = item.copy(visible = false)
                state.copy(
                    shoppingItems = shoppingItems
                )
            }
        } catch (e: Exception) {
            logcat(TAG) { e.asLog() }
        }
    }
}