package br.com.teilorsoares.pantrypal.domain.usecase.location

import br.com.teilorsoares.pantrypal.domain.model.Location

fun interface InsertLocationUseCase : suspend (Location) -> Unit