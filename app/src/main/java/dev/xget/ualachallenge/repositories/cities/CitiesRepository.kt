package dev.xget.ualachallenge.repositories.cities

import android.util.Log
import com.google.gson.Gson
import dev.xget.ualachallenge.data.cities.local.CitiesLocalDataSource
import dev.xget.ualachallenge.data.cities.remote.CitiesRemoteDataSource
import dev.xget.ualachallenge.data.utils.toCityList
import dev.xget.ualachallenge.presentation.City
import dev.xget.ualachallenge.presentation.Coordinates
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject


class CitiesRepository @Inject constructor(
    private val citiesRemoteDataSource: CitiesRemoteDataSource,
    private val citiesLocalDataSource: CitiesLocalDataSource
) {

    suspend fun getCities(): List<City> {
        //First check if there is a cities json in the cache
        val citiesJson = citiesLocalDataSource.getCitiesJson()
        val gson = Gson()

        return if (citiesJson != null) {
            Log.d("CitiesRepository", "Found cities in cache , ${citiesJson.length()}")
             citiesJson.toCityList()
        } else {
            //If there is no cities json in the cache, get the cities from the remote
            val citiesResponse = citiesRemoteDataSource.getCities()
            citiesLocalDataSource.saveCitiesJson(JSONArray(gson.toJson(citiesResponse)))
            citiesResponse.map { it.toCity() }
        }
    }
}




