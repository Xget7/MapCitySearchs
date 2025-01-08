package dev.xget.ualachallenge.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.xget.ualachallenge.data.cities.local.CitiesLocalDataSource
import dev.xget.ualachallenge.data.cities.remote.CitiesApi
import dev.xget.ualachallenge.data.cities.remote.CitiesRemoteDataSource
import dev.xget.ualachallenge.repositories.cities.CitiesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    @Singleton
    fun provideCitiesRemoteDataSource(
        citiesApi: CitiesApi,
        ioDispatcher: CoroutineDispatcher
    ) : CitiesRemoteDataSource {
        return CitiesRemoteDataSource(citiesApi = citiesApi, ioDispatcher = ioDispatcher)
    }

    @Provides
    @Singleton
    fun provideCitiesLocalDataSource(
        @ApplicationContext context: Context,
        ioDispatcher: CoroutineDispatcher
    ): CitiesLocalDataSource {
        return CitiesLocalDataSource(ioDispatcher = ioDispatcher, context = context)
    }

    @Provides
    @Singleton
    fun provideCitiesRepository(
        citiesRemoteDataSource: CitiesRemoteDataSource,
        citiesLocalDataSource: CitiesLocalDataSource
    ): CitiesRepository {
        return CitiesRepository(
            citiesRemoteDataSource = citiesRemoteDataSource,
            citiesLocalDataSource = citiesLocalDataSource
        )
    }
}