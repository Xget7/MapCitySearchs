package dev.xget.ualachallenge.data.cities.dto

import com.google.gson.annotations.SerializedName
import dev.xget.ualachallenge.presentation.City
import dev.xget.ualachallenge.presentation.Coordinates


data class CityDto(
    val country: String,
    val name: String,
    @SerializedName("_id")
    val id: String,
    val coordinates: CoordinatesDto,
){
    fun toCity(): City {
        return City(
            id = id,
            name = name,
            country = country,
            coordinates = coordinates.toCoordinates()
        )
    }
}

data class CoordinatesDto(
    val latitude: Double,
    val longitude: Double,
){
    fun toCoordinates(): Coordinates {
        return Coordinates(
            latitude = latitude,
            longitude = longitude,
        )
    }
}

