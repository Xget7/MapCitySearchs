package dev.xget.ualachallenge.data.cities.remote

import dev.xget.ualachallenge.data.cities.dto.CityDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class CitiesRemoteDataSource(
    private val citiesApi: CitiesApi,
    private val ioDispatcher: CoroutineDispatcher
) : CitiesRemoteDataSourceI {

    override suspend fun getCities(): List<CityDto> =
        withContext(ioDispatcher) {
            citiesApi.getCities()
        }
}