package dev.xget.ualachallenge.data.converters.cities
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.xget.ualachallenge.data.cities.local.entity.CoordinatesEntity

class CoordinatesConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromCoordinates(coordinates: CoordinatesEntity?): String? {
        return gson.toJson(coordinates)
    }

    @TypeConverter
    fun toCoordinates(json: String?): CoordinatesEntity? {
        return gson.fromJson(json, object : TypeToken<CoordinatesEntity>() {}.type)
    }
}
