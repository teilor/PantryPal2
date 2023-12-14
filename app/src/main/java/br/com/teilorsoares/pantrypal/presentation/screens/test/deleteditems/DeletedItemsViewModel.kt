package br.com.teilorsoares.pantrypal.presentation.screens.test.deleteditems

import androidx.lifecycle.viewModelScope
import br.com.teilorsoares.pantrypal.domain.usecase.food.GetFoodSortedByDeleteUseCase
import br.com.teilorsoares.pantrypal.domain.usecase.food.GetFoodSortedByExpireUseCase
import br.com.teilorsoares.pantrypal.presentation.arch.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class DeletedItemsViewModel(
    private val getFoodSortedByDeleteUseCase: GetFoodSortedByDeleteUseCase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel<DeletedItemsUiState, DeletedItemsUiAction>(DeletedItemsUiState()){
    init {
        collectFoods()
    }

    private fun collectFoods() {
        viewModelScope.launch {
            getFoodSortedByDeleteUseCase()
                .flowOn(ioDispatcher)
                .collect { foods ->
                    setState { state -> state.copy(foods = foods) }
                }
        }
    }
}