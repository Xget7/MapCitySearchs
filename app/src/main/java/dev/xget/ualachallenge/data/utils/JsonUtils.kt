package dev.xget.ualachallenge.data.utils

import dev.xget.ualachallenge.data.cities.dto.CityDto
import dev.xget.ualachallenge.data.cities.dto.CoordinatesDto
import dev.xget.ualachallenge.presentation.City
import dev.xget.ualachallenge.presentation.Coordinates
import org.json.JSONArray
import org.json.JSONObject

// Extension function to convert JSONObject to List<CityDto>
 fun JSONArray.toCityList(): List<City> {
    val cityList = mutableListOf<City>()

    for (i in 0 until length()) {
        val jsonObject = getJSONObject(i)
        cityList.add(jsonObject.toCity())
    }

    return cityList
}

fun JSONObject.toCity(): City {
    return City(
        id = this.getString("_id"),
        name = this.getString("name"),
        country = this.getString("country"),
        coordinates = this.optJSONObject("coordinates")?.toCoordinates() ?: defaultCoordinates()
    )
}

fun defaultCoordinates(): Coordinates {
   return Coordinates(0.0, 0.0)
}

private fun JSONObject.toCoordinates(): Coordinates {
    return Coordinates(
        latitude = this.getDouble("latitude"),
        longitude = this.getDouble("longitude"),
    )
}