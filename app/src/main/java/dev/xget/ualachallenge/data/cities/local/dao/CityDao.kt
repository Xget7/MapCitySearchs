package dev.xget.ualachallenge.data.cities.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.xget.ualachallenge.data.cities.local.entity.CityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCities(cities: List<CityEntity>)

    @Query("""
    SELECT * FROM cities 
    WHERE (:query IS NULL OR LOWER(name) LIKE LOWER(:query) || '%' OR LOWER(country) LIKE LOWER(:query) || '%')
    AND (:favorites = 0 OR isFavorite = 1)
    ORDER BY name ASC, country ASC
    """)
    fun getAllCities(query: String?, favorites: Boolean): PagingSource<Int, CityEntity>

    @Query("SELECT * FROM cities WHERE id = :id")
    suspend fun getCityById(id: Int): CityEntity?

    @Query("DELETE FROM cities")
    suspend fun deleteAllCities()

    //Update favorite city
    @Query("UPDATE cities SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteCity(id: Int, isFavorite: Boolean)


    @Query("SELECT COUNT(*) > 0 FROM cities")
    suspend fun isAnyCityExist(): Boolean
}