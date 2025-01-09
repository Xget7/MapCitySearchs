package dev.xget.ualachallenge.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dev.xget.ualachallenge.presentation.City
import dev.xget.ualachallenge.presentation.Coordinates
import dev.xget.ualachallenge.presentation.components.CitiesSearchBar
import dev.xget.ualachallenge.presentation.components.CityCardItem
import dev.xget.ualachallenge.presentation.mockCities

@Composable
fun CitiesBrowserHome(
    viewModel: CitiesBrowserHomeViewModel = hiltViewModel(),
) {
    val state = viewModel.uiState.collectAsState()

    CitiesBrowserHomeContent(
        cities = viewModel.cities.collectAsState(emptyList()).value,
        onQueryChange = viewModel::onQueryChange,
        query = viewModel.searchQuery.collectAsState().value
    )
}

@Composable
fun CitiesBrowserHomeContent(
    cities: List<City>,
    onQueryChange: (String) -> Unit = {},
    query: String = "",
) {

    val snackBarHostState = SnackbarHostState()

    Scaffold(
        containerColor = Color(0xFFF6F6F6),
        snackbarHost = { SnackbarHost(snackBarHostState) },
    ) {

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
        ) {
            CitiesSearchBar(
                query = query,
                onQueryChange = onQueryChange,
            )

            key(cities) {
                LazyColumn(
                    modifier = Modifier
                ) {
                    items(cities) {
                        CityCardItem(city = it) {

                        }
                    }
                }
            }
        }


    }

}

@Preview
@Composable
fun CitiesBrowserHomePreview() {


    CitiesBrowserHomeContent(
        cities = mockCities
    )
}