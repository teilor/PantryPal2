package br.com.teilorsoares.pantrypal.domain.usecase.shippingitem

import br.com.teilorsoares.pantrypal.domain.model.ShoppingItem

fun interface InsertShoppingItemUseCase : suspend (ShoppingItem) -> Unit