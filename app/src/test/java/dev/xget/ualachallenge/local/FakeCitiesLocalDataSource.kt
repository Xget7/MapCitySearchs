package dev.xget.ualachallenge.local

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.xget.ualachallenge.data.cities.dto.CityDto
import dev.xget.ualachallenge.data.cities.local.CitiesLocalDataSourceI
import dev.xget.ualachallenge.data.cities.local.entity.CityEntity

class FakeCitiesLocalDataSource : CitiesLocalDataSourceI {

    private val cities = mutableListOf<CityEntity>()

    override suspend fun saveCities(cities: List<CityDto>) {
        this.cities.clear()
        this.cities.addAll(cities.map { it.toEntity() })
    }

    override fun getCities(favorites: Boolean, query: String?): PagingSource<Int, CityEntity> {
        val filteredCities = cities.filter { city ->
            (query.isNullOrEmpty() || city.name.startsWith(query, ignoreCase = true) || city.country.startsWith(query, ignoreCase = true)) &&
                    (!favorites || city.isFavorite)
        }
        return FakePagingSource(filteredCities)
    }

    override suspend fun deleteAllCities() {
        cities.clear()
    }

    override suspend fun localCitiesExists(): Boolean {
        return cities.isNotEmpty()
    }

    override suspend fun setFavorite(cityId: Int, isFavorite: Boolean) {
        val index = cities.indexOfFirst { it.id == cityId.toString() }
        if (index != -1) {
            cities[index] = cities[index].copy(isFavorite = isFavorite)
        }
    }

    override suspend fun getCityById(id: Int): CityEntity? {
        return cities.firstOrNull { it.id == id.toString() }
    }

    // Helper to convert CityDto to CityEntity
    private fun CityDto.toEntity(): CityEntity {
        return CityEntity(
            id = this.id,
            name = this.name,
            country = this.country,
            isFavorite = false
        )
    }
}

// Fake PagingSource for testing
class FakePagingSource(private val data: List<CityEntity>) : PagingSource<Int, CityEntity>() {
    override fun getRefreshKey(state: PagingState<Int, CityEntity>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CityEntity> {
        return LoadResult.Page(
            data = data,
            prevKey = null,
            nextKey = null
        )
    }
}
