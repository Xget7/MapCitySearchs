package dev.xget.ualachallenge.data.cities.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import dev.xget.ualachallenge.data.cities.dto.CityDto
import dev.xget.ualachallenge.presentation.ui_data.City


@Entity(
    tableName = "cities",
    indices = [
        Index(value = ["name"], unique = false),
        Index(value = ["country"], unique = false)
    ]
)
data class CityEntity(
    @PrimaryKey() val id: String = "",
    val name: String = "",
    val country: String = "",
    val coordinates: CoordinatesEntity? = null ,
    val isFavorite: Boolean = false
){
    fun toCityDto(): CityDto {
        return CityDto(
            country = country,
            name = name,
            id = id,
            coordinates = coordinates?.toDtoCoordinates()
        )
    }

    fun toDomainModel(): City {
        return City(
            id = id,
            name = name,
            country = country,
            coordinates = coordinates?.toDomainModel(),
            isFavorite = isFavorite
        )
    }
}

