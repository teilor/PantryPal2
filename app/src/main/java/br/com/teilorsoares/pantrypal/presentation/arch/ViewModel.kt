package br.com.teilorsoares.pantrypal.presentation.arch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.teilorsoares.pantrypal.presentation.arch.action.UiAction
import br.com.teilorsoares.pantrypal.presentation.arch.state.UiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

abstract class ViewModel<State: UiState, Action: UiAction>(
    initialState: State
) : ViewModel() {
    private val viewModelState = br.com.teilorsoares.pantrypal.presentation.arch.state.State(initialState)
    private val viewModelAction = br.com.teilorsoares.pantrypal.presentation.arch.action.Action<Action>()

    val state = viewModelState.state.stateIn(
        scope = viewModelScope,
        initialValue = initialState,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
    )
    val action = viewModelAction.action

    protected suspend fun setState(state: (State) -> State){
        viewModelState.setState(state)
    }

    protected suspend fun sendAction(action: () -> Action){
        viewModelAction.sendAction(action)
    }
}
