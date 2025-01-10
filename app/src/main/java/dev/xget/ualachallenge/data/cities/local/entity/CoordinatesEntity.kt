package dev.xget.ualachallenge.data.cities.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.xget.ualachallenge.data.cities.dto.CoordinatesDto
import dev.xget.ualachallenge.presentation.ui_data.Coordinates

@Entity(tableName = "coordinates")
data class CoordinatesEntity(
    @PrimaryKey
    val id: String = "",
    val longitude: Double,
    val latitude: Double
){
    fun toDtoCoordinates(): CoordinatesDto {
        return CoordinatesDto(
            lon = longitude,
            lat = latitude
        )
    }

    fun toDomainModel(): Coordinates {
        return Coordinates(
            latitude = latitude,
            longitude = longitude
        )
    }
}