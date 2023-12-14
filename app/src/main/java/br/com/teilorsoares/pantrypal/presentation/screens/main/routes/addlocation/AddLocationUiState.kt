package br.com.teilorsoares.pantrypal.presentation.screens.main.routes.addlocation

import br.com.teilorsoares.pantrypal.presentation.arch.state.UiState

data class AddLocationUiState(
    val locationName: String = ""
) : UiState
