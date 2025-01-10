package dev.xget.ualachallenge.data.cities.remote

import dev.xget.ualachallenge.data.cities.dto.CityDto

interface CitiesRemoteDataSourceI {

    suspend fun getCities(): List<CityDto>
}