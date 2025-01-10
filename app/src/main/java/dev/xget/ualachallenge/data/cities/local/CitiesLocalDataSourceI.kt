package dev.xget.ualachallenge.data.cities.local

import androidx.paging.PagingSource
import dev.xget.ualachallenge.data.cities.dto.CityDto
import dev.xget.ualachallenge.data.cities.local.entity.CityEntity
import kotlinx.coroutines.flow.Flow

interface CitiesLocalDataSourceI {
    suspend fun saveCities(cities: List<CityDto>)
    fun getCities(favorites: Boolean = false, query: String? = null): PagingSource<Int, CityEntity>

    suspend fun deleteAllCities()
    suspend fun localCitiesExists(): Boolean

   suspend fun setFavorite(cityId: Int, isFavorite: Boolean)

   suspend fun getCityById(id: Int): CityEntity?
}