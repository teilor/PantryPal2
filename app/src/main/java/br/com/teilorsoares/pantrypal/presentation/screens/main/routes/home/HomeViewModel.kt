package br.com.teilorsoares.pantrypal.presentation.screens.main.routes.home

import androidx.lifecycle.viewModelScope
import br.com.teilorsoares.pantrypal.domain.model.Food
import br.com.teilorsoares.pantrypal.domain.model.ShoppingItem
import br.com.teilorsoares.pantrypal.domain.usecase.food.DeleteFoodUseCase
import br.com.teilorsoares.pantrypal.domain.usecase.food.GetFoodSortedByExpireUseCase
import br.com.teilorsoares.pantrypal.domain.usecase.food.UpdateFoodDiscardUseCase
import br.com.teilorsoares.pantrypal.domain.usecase.food.UpdateFoodQuantityUseCase
import br.com.teilorsoares.pantrypal.domain.usecase.shippingitem.InsertShoppingItemUseCase
import br.com.teilorsoares.pantrypal.presentation.arch.ViewModel
import br.com.teilorsoares.pantrypal.presentation.screens.main.navigation.MainNavigationEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import logcat.asLog
import logcat.logcat
import java.util.*

private const val TAG = "HomeViewModel"

class HomeViewModel(
    private val getFoodSortedByExpireUseCase: GetFoodSortedByExpireUseCase,
    private val updateFoodDiscardUseCase: UpdateFoodDiscardUseCase,
    private val deleteFoodUseCase: DeleteFoodUseCase,
    private val insertShoppingItemUseCase: InsertShoppingItemUseCase,
    private val updateFoodQuantityUseCase: UpdateFoodQuantityUseCase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel<HomeUiState, HomeUiAction>(HomeUiState()) {
    private var collectFood: Job? = null

    init {
        collectFood()
    }

    private fun collectFood(consumed: Boolean = false) {
        collectFood?.cancel()
        collectFood = viewModelScope.launch {
            setState { state -> state.copy(consumed = consumed) }
            getFoodSortedByExpireUseCase(consumed)
                .flowOn(ioDispatcher)
                .collect { foods ->
                    setState { state -> state.copy(foods = foods) }
                }
        }
    }

    fun clickedTab(tab: Int) {
        viewModelScope.launch {
            try {
                setState { state -> state.copy(selectedTabIndex = tab) }
                collectFood(tab == 1)
            } catch (e: Exception) {
                logcat(TAG) { e.asLog() }
            }
        }
    }

    fun clickedDiscard(food: Food) {
        viewModelScope.launch {
            try {
                updateFoodVisibility(food)
                updateFoodDiscardUseCase(food.id, true)
            } catch (e: Exception) {
                logcat(TAG) { e.asLog() }
            }
        }
    }

    fun clickedQuantity(food: Food) {
        viewModelScope.launch {
            try {
                setState { state ->
                    state.copy(
                        selectedFood = food,
                        expandedQuantity = food.quantity > 0,
                        expandedConsumedFood = food.quantity < 1
                    )
                }
            } catch (e: Exception) {
                logcat(TAG) { e.asLog() }
            }
        }
    }

    fun clickedQuantityChange(quantity: String) {
        viewModelScope.launch {
            try {
                if (quantity.isEmpty()) return@launch
                val food = state.value.selectedFood ?: return@launch

                updateFoodQuantityUseCase(
                    food.id,
                    quantity.toLong()
                )

                if (quantity.toLong() < 1) {
                    setState { state ->
                        state.copy(
                            expandedQuantity = false,
                            expandedConsumedFood = true
                        )
                    }
                }
            } catch (e: Exception) {
                logcat(TAG) { e.asLog() }
            }
        }
    }

    fun clickedQuantityDismiss() {
        viewModelScope.launch {
            setState { state -> state.copy(selectedFood = null, expandedQuantity = false) }
        }
    }

    fun clickedAddShoppingItemConfirm() {
        viewModelScope.launch {
            try {
                setState { state -> state.copy(expandedConsumedFood = false) }
                val food = state.value.selectedFood ?: return@launch

                insertShoppingItemUseCase(
                    ShoppingItem(
                        id = UUID.randomUUID().toString(),
                        name = food.name,
                        purchased = false
                    )
                )
                updateFoodVisibility(food)
                deleteFoodUseCase(food.id)
            } catch (e: Exception) {
                logcat(TAG) { e.asLog() }
            }
        }
    }

    fun clickedDeleteConfirm() {
        viewModelScope.launch {
            try {
                setState { state -> state.copy(expandedConsumedFood = false) }
                val food = state.value.selectedFood ?: return@launch

                updateFoodVisibility(food)
                deleteFoodUseCase(food.id)
            } catch (e: Exception) {
                logcat(TAG) { e.asLog() }
            }
        }
    }

    fun clickedDiscardDismiss() {
        clickedDeleteConfirm()
    }

    // Method for updating the visibility of the item in the list
    // Responsible for the animation of the item disappearing
    private suspend fun updateFoodVisibility(
        food: Food
    ) {
        try {
            setState { state ->
                val foods = state.foods.toMutableList()
                val index = foods.indexOfFirst { it.id == food.id }
                foods[index] = food.copy(visible = false)
                state.copy(
                    foods = foods
                )
            }
            withContext(ioDispatcher) { delay(400) }
        } catch (e: Exception) {
            logcat(TAG) { e.asLog() }
        }
    }

}