package dev.xget.ualachallenge.data.cities.local

import android.content.Context
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutput
import java.io.ObjectOutputStream


class CitiesLocalDataSource(
    private val context: Context,
    private val ioDispatcher: CoroutineDispatcher
) : CitiesLocalDataSourceI {

    override  suspend fun saveCitiesJson(citiesJson: JSONObject) {
        withContext(ioDispatcher) {
            // Save the citiesJson to the cache directory
            val out: ObjectOutput = ObjectOutputStream(
                FileOutputStream(
                    File(
                        context.cacheDir,
                        ""
                    ).toString() + "cacheFile.srl"
                )
            )
            out.writeObject(citiesJson)
            out.close()
        }

    }

    override suspend fun getCitiesJson(): JSONObject? {
        return withContext(ioDispatcher) {
            val input = ObjectInputStream(
                FileInputStream(
                    File(
                        File(
                            context.cacheDir,
                            ""
                        ).toString() + "cacheFile.srl"
                    )
                )
            )
            val jsonObject = input.readObject() as JSONObject?
            input.close()
            return@withContext jsonObject
        }
    }

}