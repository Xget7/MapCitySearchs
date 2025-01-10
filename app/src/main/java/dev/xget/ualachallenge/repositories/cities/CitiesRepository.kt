package dev.xget.ualachallenge.repositories.cities

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import dev.xget.ualachallenge.data.cities.local.CitiesLocalDataSource
import dev.xget.ualachallenge.data.cities.local.CitiesLocalDataSourceI
import dev.xget.ualachallenge.data.cities.local.entity.CityEntity
import dev.xget.ualachallenge.data.cities.remote.CitiesRemoteDataSource
import dev.xget.ualachallenge.data.cities.remote.CitiesRemoteDataSourceI
import dev.xget.ualachallenge.presentation.ui_data.City
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class CitiesRepository @Inject constructor(
    private val citiesRemoteDataSource: CitiesRemoteDataSourceI,
    private val citiesLocalDataSource: CitiesLocalDataSourceI
) {

    fun getCities(
        favorites: Boolean,
        query: String?
    ): Flow<PagingData<City>> {
        return flow {
            // Check if local data exists
            val localExists = citiesLocalDataSource.localCitiesExists()
            Log.d("CitiesRepository", "Local cities exist: $localExists")

            if (!localExists) {
                // Fetch remote data and save to cache if local data does not exist
                val remoteCities = citiesRemoteDataSource.getCities()
                citiesLocalDataSource.saveCities(remoteCities)
            }

            // Use PagingSource for paginated local data
            emitAll(
                Pager(
                    config = PagingConfig(pageSize = 20, prefetchDistance = 2)
                ) {
                    citiesLocalDataSource.getCities(favorites, query)
                }.flow.map { pagingData ->
                    pagingData.map { it.toDomainModel() }
                }
            )
        }
    }

    suspend fun setFavorite(cityId: Int, isFavorite: Boolean) {
        citiesLocalDataSource.setFavorite(cityId, isFavorite)
    }

    suspend fun getCityById(id: Int): City? {
        return citiesLocalDataSource.getCityById(id)?.toDomainModel()
    }
}





