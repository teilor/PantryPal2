package br.com.teilorsoares.pantrypal.domain.model

data class Food(
    val id: String,
    val name: String,
    val photo: String,
    val createdDate: Long,
    val expiryDate: Long,
    val deletedDate: Long?,
    val location: Location,
    val meal: Meal,
    val quantity: Long,
    val consumedOrDiscarded: Boolean,
    val status: Status,
    val visible: Boolean = true
) {
    enum class Meal {
        BREAKFAST,
        LUNCH,
        SNACK,
        DINNER
    }

    enum class Status {
        EXPIRED,
        EXPIRING,
        OK
    }

    companion object {
        fun default() = Food(
            id = "",
            name = "",
            photo = "",
            createdDate = 0,
            expiryDate = 0,
            deletedDate = null,
            location = Location.default(),
            meal = Meal.BREAKFAST,
            quantity = 0,
            consumedOrDiscarded = false,
            status = Status.OK
        )
    }
}
