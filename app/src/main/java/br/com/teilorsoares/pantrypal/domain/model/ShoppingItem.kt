package br.com.teilorsoares.pantrypal.domain.model

data class ShoppingItem(
    val id: String,
    val name: String,
    val purchased: Boolean,
    val visible: Boolean = true
)
