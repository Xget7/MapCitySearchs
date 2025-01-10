package dev.xget.ualachallenge.repositories

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.map
import androidx.paging.testing.asSnapshot
import dev.xget.ualachallenge.data.cities.dto.CityDto
import dev.xget.ualachallenge.data.cities.local.CitiesLocalDataSource
import dev.xget.ualachallenge.data.cities.local.CitiesLocalDataSourceI
import dev.xget.ualachallenge.data.cities.local.entity.CityEntity
import dev.xget.ualachallenge.data.cities.remote.CitiesRemoteDataSource
import dev.xget.ualachallenge.local.FakeCitiesLocalDataSource
import dev.xget.ualachallenge.presentation.ui_data.City
import dev.xget.ualachallenge.remote.FakeCitiesRemoteDataSource
import dev.xget.ualachallenge.repositories.cities.CitiesRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class CitiesRepositoryTest {


     lateinit var citiesLocalDataSource: FakeCitiesLocalDataSource
     lateinit  var citiesRemoteDataSource: FakeCitiesRemoteDataSource

    private lateinit var citiesRepository: CitiesRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        citiesLocalDataSource = FakeCitiesLocalDataSource()
        citiesRemoteDataSource = FakeCitiesRemoteDataSource()

        citiesRepository = CitiesRepository(citiesRemoteDataSource, citiesLocalDataSource)
    }

    @Test
    fun `test search with valid prefix`() = runTest {
        // Given a list of cities
        val cities = listOf(
            CityDto(id = "1", name = "Alabama", country = "US",),
            CityDto(id = "2", name = "Albuquerque", country = "US"),
            CityDto(id = "3", name = "Anaheim", country = "US"),
            CityDto(id = "4", name = "Arizona", country = "US")
        )
        citiesLocalDataSource.saveCities(cities)


        // Search for cities starting with "Al"
        val result = citiesRepository.getCities(favorites = false, query = "Al").asSnapshot()

        println("REsult like: ")
        println(result)
        // Assert that only "Alabama" and "Albuquerque" are returned
        assertTrue(result.map { it.name }.containsAll(listOf("Alabama", "Albuquerque")))
        assertTrue(result.size == 2)
    }

    @Test
    fun `test search with empty query`() = runTest {
        // Given a list of cities
        val cities = listOf(
            CityDto(id = "1", name = "Alabama", country = "US"),
            CityDto(id = "2", name = "Albuquerque", country = "US"),
            CityDto(id = "3", name = "Anaheim", country = "US"),
            CityDto(id = "4", name = "Arizona", country = "US")
        )
        citiesLocalDataSource.saveCities(cities)

        // Search with an empty query
        val result = citiesRepository.getCities(favorites = false, query = "").asSnapshot()

        // Assert that all cities are returned
        assertTrue(result.map { it.name }.containsAll(listOf("Alabama", "Albuquerque", "Anaheim", "Arizona")))
        assertTrue(result.size == 4)
    }

    @Test
    fun `test search with invalid prefix`() = runTest {
        // Given a list of cities
        val cities = listOf(
            CityDto(id = "1", name = "Alabama", country = "US"),
            CityDto(id = "2", name = "Albuquerque", country = "US"),
            CityDto(id = "3", name = "Anaheim", country = "US"),
            CityDto(id = "4", name = "Arizona", country = "US")
        )
        citiesLocalDataSource.saveCities(cities)

        // Search with a prefix that doesn't match any city
        val result = citiesRepository.getCities(favorites = false, query = "X").asSnapshot()

        // Assert that no cities are returned
        assertTrue(result.isEmpty())
    }

    @Test
    fun `test search with case insensitive query`() = runTest {
        // Given a list of cities
        val cities = listOf(
            CityDto(id = "1", name = "Alabama", country = "US"),
            CityDto(id = "2", name = "Albuquerque", country = "US"),
            CityDto(id = "3", name = "Anaheim", country = "US"),
            CityDto(id = "4", name = "Arizona", country = "US")
        )
        citiesLocalDataSource.saveCities(cities)

        // Search with a case-insensitive prefix
        val result = citiesRepository.getCities(favorites = false, query = "al").asSnapshot()

        // Assert that "Alabama" and "Albuquerque" are returned
        assertTrue(result.map { it.name }.containsAll(listOf("Alabama", "Albuquerque")))
        assertTrue(result.size == 2)
    }

    @Test
    fun `test search with partial prefix`() = runTest {
        // Given a list of cities
        val cities = listOf(
            CityDto(id = "1", name = "Alabama", country = "US"),
            CityDto(id = "2", name = "Albuquerque", country = "US"),
            CityDto(id = "3", name = "Anaheim", country = "US"),
            CityDto(id = "4", name = "Arizona", country = "US")
        )
        citiesLocalDataSource.saveCities(cities)

        // Search for a partial prefix
        val result = citiesRepository.getCities(favorites = false, query = "Alab").asSnapshot()

        // Assert that only "Alabama" is returned
        assertTrue(result.map { it.name }.contains("Alabama"))
        assertTrue(result.size == 1)
    }

    @Test
    fun `test search with favorites only`() = runTest {
        // Given a list of cities with some marked as favorites
        val cities = listOf(
            CityDto(id = "1", name = "Alabama", country = "US"),
            CityDto(id = "2", name = "Albuquerque", country = "US"),
            CityDto(id = "3", name = "Anaheim", country = "US"),
            CityDto(id = "4", name = "Arizona", country = "US")
        )
        citiesLocalDataSource.saveCities(cities)
        citiesRepository.setFavorite(cities[0].id.toInt(), true)
        citiesRepository.setFavorite(cities[3].id.toInt(), true)

        // Search for favorites only
        val result = citiesRepository.getCities(favorites = true, query = "").asSnapshot()

        // Assert that only favorites are returned
        assertTrue(result.map { it.name }.containsAll(listOf("Alabama", "Arizona")))
        assertTrue(result.size == 2)
    }

}
