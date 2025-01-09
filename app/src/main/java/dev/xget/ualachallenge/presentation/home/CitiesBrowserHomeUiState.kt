package dev.xget.ualachallenge.presentation.home

import dev.xget.ualachallenge.presentation.City
import dev.xget.ualachallenge.presentation.mockCities

data class CitiesBrowserHomeUiState(
    val isError: String? = null,
    val isLoading: Boolean = false,
)