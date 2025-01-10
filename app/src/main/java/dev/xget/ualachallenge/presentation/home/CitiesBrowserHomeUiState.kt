package dev.xget.ualachallenge.presentation.home

import dev.xget.ualachallenge.presentation.ui_data.City

data class CitiesBrowserHomeUiState(
    val errorMsg: String? = null,
    val isLoading: Boolean = false,
    val filterFavorites: Boolean = false,
    val selectedCity: City? = null,
    val navigateCityId: Int? = null,
    val navToMap: Boolean = false,
)