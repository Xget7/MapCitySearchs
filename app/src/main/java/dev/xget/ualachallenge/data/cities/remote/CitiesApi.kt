package dev.xget.ualachallenge.data.cities.remote

import dev.xget.ualachallenge.data.cities.dto.CityDto
import retrofit2.http.GET

interface CitiesApi {
    //https://gist.githubusercontent.com/hernan-uala/dce8843a8edbe0b0018b32e137bc2b3a/raw/0996accf70cb0ca0e16f9a99e0ee185fafca7af1/cities.json
    @GET("")
   suspend fun getCities(): List<CityDto>
}