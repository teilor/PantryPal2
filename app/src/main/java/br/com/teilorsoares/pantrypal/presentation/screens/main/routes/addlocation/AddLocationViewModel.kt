package br.com.teilorsoares.pantrypal.presentation.screens.main.routes.addlocation

import androidx.lifecycle.viewModelScope
import br.com.teilorsoares.pantrypal.domain.model.Location
import br.com.teilorsoares.pantrypal.domain.usecase.location.InsertLocationUseCase
import br.com.teilorsoares.pantrypal.presentation.arch.ViewModel
import br.com.teilorsoares.pantrypal.presentation.screens.main.navigation.MainNavigationEvent
import kotlinx.coroutines.launch
import java.util.UUID

class AddLocationViewModel(
    private val insertLocationUseCase: InsertLocationUseCase
) : ViewModel<AddLocationUiState, AddLocationUiAction>(AddLocationUiState()) {

    fun changedLocationName(locationName: String) {
        viewModelScope.launch {
            setState { state -> state.copy(locationName = locationName) }
        }
    }

    fun clickedSaveLocation() {
        viewModelScope.launch {
            val locationName = state.value.locationName
            if (locationName.isNotBlank()) {
                insertLocationUseCase(
                    Location(
                        id = UUID.randomUUID().toString(),
                        name = locationName
                    )
                )

                sendAction { AddLocationUiAction.FinishScreen }
            }
        }
    }

    fun clickedBack() {
        MainNavigationEvent.navigateBack()
    }
}