package dev.xget.ualachallenge.presentation.ui_data

import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState

data class City(
    val country: String,
    val name: String,
    val id: String,
    val coordinates: Coordinates? = null,
    val isFavorite: Boolean = false
)

data class Coordinates(
    val latitude: Double,
    val longitude: Double,
){
    fun toCameraPositionState() = CameraPositionState(
        CameraPosition.fromLatLngZoom(LatLng(latitude, longitude), 15f)
    )

    fun toLatLng() = LatLng(latitude, longitude)
}

val mockCities = listOf(
    City(
        country = "UA",
        name = "Hurzuf",
        id = "707860",
        coordinates = Coordinates(latitude = 44.549999, longitude = 34.283333)
    ),
    City(
        country = "RU",
        name = "Novinki",
        id = "519188",
        coordinates = Coordinates(latitude = 55.683334, longitude = 37.666668)
    ),
    City(
        country = "NP",
        name = "Gorkhā",
        id = "1283378",
        coordinates = Coordinates(latitude = 28.0, longitude = 84.633331)
    ),
    City(
        country = "IN",
        name = "State of Haryāna",
        id = "1270260",
        coordinates = Coordinates(latitude = 29.0, longitude = 76.0)
    ),
    City(
        country = "UA",
        name = "Holubynka",
        id = "708546",
        coordinates = Coordinates(latitude = 44.599998, longitude = 33.900002)
    ),
    City(
        country = "NP",
        name = "Bāgmatī Zone",
        id = "1283710",
        coordinates = Coordinates(latitude = 28.0, longitude = 85.416664)
    ),
    City(
        country = "RU",
        name = "Mar’ina Roshcha",
        id = "529334",
        coordinates = Coordinates(latitude = 55.796391, longitude = 37.611111)
    ),
    City(
        country = "IN",
        name = "Republic of India",
        id = "1269750",
        coordinates = Coordinates(latitude = 20.0, longitude = 77.0)
    ),
    City(
        country = "NP",
        name = "Kathmandu",
        id = "1283240",
        coordinates = Coordinates(latitude = 27.716667, longitude = 85.316666)
    ),
    City(
        country = "UA",
        name = "Laspi",
        id = "703363",
        coordinates = Coordinates(latitude = 44.416668, longitude = 33.733334)
    ),
    City(
        country = "VE",
        name = "Merida",
        id = "3632308",
        coordinates = Coordinates(latitude = 8.598333, longitude = -71.144997)
    ),
    City(
        country = "RU",
        name = "Vinogradovo",
        id = "473537",
        coordinates = Coordinates(latitude = 55.423332, longitude = 38.545555)
    ),
    City(
        country = "IQ",
        name = "Qarah Gawl al ‘Ulyā",
        id = "384848",
        coordinates = Coordinates(latitude = 35.353889, longitude = 45.6325)
    ),
    City(
        country = "RU",
        name = "Cherkizovo",
        id = "569143",
        coordinates = Coordinates(latitude = 55.800835, longitude = 37.728889)
    ),
    City(
        country = "UA",
        name = "Alupka",
        id = "713514",
        coordinates = Coordinates(latitude = 44.416668, longitude = 34.049999)
    )
)