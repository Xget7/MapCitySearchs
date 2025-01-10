package dev.xget.ualachallenge.presentation.maps

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.xget.ualachallenge.data.cities.dto.CoordinatesDto
import dev.xget.ualachallenge.presentation.ui_data.Coordinates
import dev.xget.ualachallenge.repositories.cities.CitiesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CityMapScreenViewModel @Inject constructor(
     savedStateHandle: SavedStateHandle,
    private val citiesRepository: CitiesRepository
) : ViewModel() {


    private val _cityCoordinates = MutableStateFlow<Coordinates?>(null)
    val cityCoordinates: StateFlow<Coordinates?> = _cityCoordinates

    init {
        val cityId: String? = savedStateHandle["cityId"]
        if (!cityId.isNullOrEmpty()) {
            getCityCoordinatesById(cityId)
        }
    }

    private fun getCityCoordinatesById(cityId: String) {
        viewModelScope.launch {
            try {
                val city = citiesRepository.getCityById(cityId.toInt())
                _cityCoordinates.value = city?.coordinates
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}