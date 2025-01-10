package dev.xget.ualachallenge.data.cities.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.xget.ualachallenge.data.cities.local.dao.CityDao
import dev.xget.ualachallenge.data.cities.local.entity.CityEntity
import dev.xget.ualachallenge.data.cities.local.entity.CoordinatesEntity
import dev.xget.ualachallenge.data.converters.cities.CoordinatesConverter

@Database(entities = [CityEntity::class, CoordinatesEntity::class], version = 2)
@TypeConverters(CoordinatesConverter::class)
abstract class CitiesDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao

    companion object {
        @Volatile
        private var INSTANCE: CitiesDatabase? = null

        fun getDatabase(context: Context): CitiesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CitiesDatabase::class.java,
                    "cities_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}