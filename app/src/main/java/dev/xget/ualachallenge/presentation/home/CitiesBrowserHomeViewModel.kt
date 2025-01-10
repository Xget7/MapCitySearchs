package dev.xget.ualachallenge.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.xget.ualachallenge.presentation.ui_data.City
import dev.xget.ualachallenge.repositories.cities.CitiesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
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

    private val _filterFavorites = MutableStateFlow(false)

    val citiesFlow = combine(
        _searchQuery,
        _filterFavorites
    ) { query, favorites ->
        query to favorites
    }.flatMapLatest { (query, favorites) ->
        citiesRepository.getCities(favorites, query)
    }.cachedIn(viewModelScope)

    init {
        collectCities()
    }

    private fun collectCities() {
        viewModelScope.launch(Dispatchers.IO) {
            citiesFlow
                .onStart { _uiState.update { it.copy(isLoading = true) } }
                .catch { e -> _uiState.update { it.copy(errorMsg = e.message, isLoading = false) } }
                .collect {
                    _uiState.update { it.copy(isLoading = false) }

                }
        }
    }


    fun onQueryChange(searchText: String) {
        Log.d("CitiesBrowserHomeViewModel", "onQueryChange: $searchText")
        _searchQuery.value = searchText
    }


    fun onFilterFavoritesChange() {
        _uiState.value = uiState.value.copy(filterFavorites = uiState.value.filterFavorites.not())
        _filterFavorites.value = uiState.value.filterFavorites
    }

    fun onCityClick(city: City, navigateOrSelect: Boolean) {
        Log.d("CitiesBrowserHomeViewModel", "onCityClick: $city , $navigateOrSelect")
        if (navigateOrSelect) {
            _uiState.value = uiState.value.copy(selectedCity = city)
        } else {
            _uiState.value = uiState.value.copy(
                selectedCity = null,
                navToMap = true,
                navigateCityId = city.id.toIntOrNull()
            )
        }

    }

    fun onFavoriteClick(cityId: Int, isFavorite: Boolean) {
        Log.d("CitiesBrowserHomeViewModel", "onFavoriteClick: $cityId, $isFavorite")
        viewModelScope.launch {
            citiesRepository.setFavorite(cityId, isFavorite)
        }
    }

    fun resetNavToMap() {
        _uiState.value = _uiState.value.copy(navToMap = false)
    }
}