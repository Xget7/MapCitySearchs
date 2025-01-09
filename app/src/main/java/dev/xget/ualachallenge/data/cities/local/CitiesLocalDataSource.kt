package dev.xget.ualachallenge.data.cities.local

import android.content.Context
import dev.xget.ualachallenge.data.cities.dto.CityDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.File



class CitiesLocalDataSource(
    private val context: Context,
    private val ioDispatcher: CoroutineDispatcher
) : CitiesLocalDataSourceI {
    override suspend fun saveCities(cities: List<CityDto>) {

    }

    override suspend fun getCities(): List<CityDto>? {
        TODO("Not yet implemented")
    }

}
