package dev.xget.ualachallenge.presentation

data class City(
    val country: String,
    val name: String,
    val id: String,
    val coordinates: Coordinates,
)

data class Coordinates(
    val latitude: Double,
    val longitude: Double,
)
