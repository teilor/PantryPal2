package br.com.teilorsoares.pantrypal.domain.model

data class Location(
    val id: String,
    val name: String
) {
    companion object {
        fun default() = Location(
            id = "",
            name = ""
        )
    }
}
