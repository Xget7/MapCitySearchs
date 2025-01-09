package dev.xget.ualachallenge.data.cities.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dev.xget.ualachallenge.data.cities.local.entity.CityEntity

@Dao
interface CityDao {

    @Insert
    suspend fun insertCities(cities: List<CityEntity>)

    @Query("SELECT * FROM cities")
    suspend fun getAllCities(): List<CityEntity>

    @Query("SELECT * FROM cities WHERE id = :id")
    suspend fun getCityById(id: Int): CityEntity?

    @Query("DELETE FROM cities")
    suspend fun deleteAllCities()

    //Update favorite city
    @Query("UPDATE cities SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteCity(id: Int, isFavorite: Boolean)

}