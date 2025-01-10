package dev.xget.ualachallenge.remote

import dev.xget.ualachallenge.data.cities.dto.CityDto
import dev.xget.ualachallenge.data.cities.remote.CitiesRemoteDataSourceI

class FakeCitiesRemoteDataSource : CitiesRemoteDataSourceI {

    private val cities = mutableListOf<CityDto>()

    fun addCities(cities: List<CityDto>) {
        this.cities.clear()
        this.cities.addAll(cities)
    }



    override suspend fun getCities(): List<CityDto> {
        return cities
    }


}