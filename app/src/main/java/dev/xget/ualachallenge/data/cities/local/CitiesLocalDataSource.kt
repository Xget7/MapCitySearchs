package dev.xget.ualachallenge.data.cities.local

import android.content.Context
import androidx.paging.PagingSource
import dev.xget.ualachallenge.data.cities.dto.CityDto
import dev.xget.ualachallenge.data.cities.local.dao.CityDao
import dev.xget.ualachallenge.data.cities.local.entity.CityEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.File



class CitiesLocalDataSource(
    private val cityDao: CityDao,
    private val ioDispatcher: CoroutineDispatcher
) : CitiesLocalDataSourceI {

    override suspend fun saveCities(cities: List<CityDto>) {
        withContext(ioDispatcher) {
            cityDao.insertCities(cities.map { it.toCityEntity() })
        }
    }

    override fun getCities(
        favorites: Boolean,
        query: String?
    ): PagingSource<Int, CityEntity> {
        return cityDao.getAllCities(
            favorites = favorites,
            query = query
        )
    }

    override suspend fun deleteAllCities() {
        withContext(ioDispatcher) {
            cityDao.deleteAllCities()
        }
    }

    override suspend fun localCitiesExists(): Boolean {
        return cityDao.isAnyCityExist()
    }

    override suspend fun setFavorite(cityId: Int, isFavorite: Boolean) {
        withContext(ioDispatcher) {
            cityDao.updateFavoriteCity(cityId, isFavorite)
        }
    }

    override suspend fun getCityById(id: Int): CityEntity? {
        return withContext(ioDispatcher) {
            cityDao.getCityById(id)
        }
    }


}
