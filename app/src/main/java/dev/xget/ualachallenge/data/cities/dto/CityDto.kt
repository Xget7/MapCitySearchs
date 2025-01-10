package dev.xget.ualachallenge.data.cities.dto

import com.google.gson.annotations.SerializedName
import dev.xget.ualachallenge.data.cities.local.entity.CityEntity
import dev.xget.ualachallenge.data.cities.local.entity.CoordinatesEntity
import dev.xget.ualachallenge.presentation.ui_data.City
import dev.xget.ualachallenge.presentation.ui_data.Coordinates


data class CityDto(
    val country: String,
    val name: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("coord")
    val coordinates: CoordinatesDto? = null
){
    fun toCity(): City {
        return City(
            id = id,
            name = name,
            country = country,
            coordinates = coordinates?.toCoordinates()
        )
    }

    fun toCityEntity(): CityEntity {
        return CityEntity(
            id = id,
            name = name,
            country = country,
            coordinates = coordinates?.toEntityCoordinates()
        )
    }
}

data class CoordinatesDto(
    val lon: Double,
    val lat: Double,
){
    fun toCoordinates(): Coordinates {
        return Coordinates(
            latitude = lat,
            longitude = lon,
        )
    }

    fun toEntityCoordinates(): CoordinatesEntity {
        return CoordinatesEntity(
            latitude = lat,
            longitude = lon,
        )
    }
}

