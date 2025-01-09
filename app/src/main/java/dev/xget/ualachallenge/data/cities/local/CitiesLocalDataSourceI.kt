package dev.xget.ualachallenge.data.cities.local

import dev.xget.ualachallenge.data.cities.dto.CityDto
import dev.xget.ualachallenge.presentation.City
import org.json.JSONArray
import org.json.JSONObject

interface CitiesLocalDataSourceI {
    suspend fun saveCities(cities: List<CityDto>)
    suspend fun getCities(): List<CityDto>?
}