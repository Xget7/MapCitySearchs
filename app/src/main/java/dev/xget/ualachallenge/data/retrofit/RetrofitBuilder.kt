package dev.xget.ualachallenge.data.retrofit

import dev.xget.ualachallenge.data.cities.remote.CitiesApi
import dev.xget.ualachallenge.data.utils.DataConstants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {

    fun getCitiesRemoteDataSource(): CitiesApi {
        return retrofit.create(CitiesApi::class.java)
    }
}