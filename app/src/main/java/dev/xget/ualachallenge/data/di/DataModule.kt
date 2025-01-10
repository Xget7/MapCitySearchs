package dev.xget.ualachallenge.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.xget.ualachallenge.data.cities.local.dao.CityDao
import dev.xget.ualachallenge.data.cities.local.database.CitiesDatabase
import dev.xget.ualachallenge.data.cities.remote.CitiesApi
import dev.xget.ualachallenge.data.utils.DataConstants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideCitiesApi(retrofit: Retrofit): CitiesApi {
        return retrofit.create(CitiesApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCityDao(
        @ApplicationContext context: Context
    ): CityDao {
        return CitiesDatabase.getDatabase(context).cityDao()
    }

}
