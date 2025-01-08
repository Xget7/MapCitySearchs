package dev.xget.ualachallenge.data.cities.local

import org.json.JSONObject

interface CitiesLocalDataSourceI {
    suspend fun saveCitiesJson(citiesJson: JSONObject)
    suspend fun getCitiesJson(): JSONObject?
}