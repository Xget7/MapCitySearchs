package dev.xget.ualachallenge.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.xget.ualachallenge.presentation.City
import dev.xget.ualachallenge.repositories.cities.CitiesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CitiesBrowserHomeViewModel @Inject constructor(
    private val citiesRepository: CitiesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CitiesBrowserHomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()


    private val _cities = MutableStateFlow(emptyList<City>())
    val cities = searchQuery.combine(
        _cities
    ){ query, cities ->
        if (query.isEmpty()) {
            cities
        } else {
            cities.filter {
                it.name.contains(query, false)
            }
        }

    }

    init {
        getCities()
    }

    private fun getCities() {
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(isLoading = true)
            val cities = citiesRepository.getCities()

            //order by alphabetical order (city first, country after)
            _cities.value = cities.sortedBy { it.name }.sortedBy { it.country }
        }
    }


    fun onQueryChange(searchText: String) {
        Log.d("CitiesBrowserHomeViewModel", "onQueryChange: $searchText")
         _searchQuery.value = searchText
    }


}