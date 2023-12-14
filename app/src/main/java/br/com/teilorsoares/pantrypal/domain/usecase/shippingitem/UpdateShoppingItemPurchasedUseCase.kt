package br.com.teilorsoares.pantrypal.domain.usecase.shippingitem

fun interface UpdateShoppingItemPurchasedUseCase : suspend (String, Boolean) -> Unit