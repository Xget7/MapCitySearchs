package dev.xget.ualachallenge.data.cities.local.entity

import androidx.room.Entity

@Entity(tableName = "coordinates")
data class CoordinatesEntity(
    val longitude: Double,
    val latitude: Double
)