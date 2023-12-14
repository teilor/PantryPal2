package br.com.teilorsoares.pantrypal.domain.usecase.shippingitem

import br.com.teilorsoares.pantrypal.domain.model.ShoppingItem
import kotlinx.coroutines.flow.Flow

fun interface GetShoppingItemUseCase : (Boolean) -> Flow<List<ShoppingItem>>