package dev.xget.ualachallenge.data.cities.local.entity

@androidx.room.Entity(tableName = "cities")
data class CityEntity(
    val id: String,
    val name: String,
    val country: String,
    val coordinates: CoordinatesEntity,
    val isFavorite: Boolean = false
)

