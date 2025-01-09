package dev.xget.ualachallenge.data.cities.remote

import dev.xget.ualachallenge.data.cities.dto.CityDto
import retrofit2.http.GET

interface CitiesApi {
    @GET("cities.json")
   suspend fun getCities(): List<CityDto>
}