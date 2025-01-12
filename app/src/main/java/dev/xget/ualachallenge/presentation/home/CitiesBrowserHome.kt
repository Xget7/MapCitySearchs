package dev.xget.ualachallenge.presentation.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import dev.xget.ualachallenge.presentation.components.CitiesSearchBar
import dev.xget.ualachallenge.presentation.components.CityCardItem
import dev.xget.ualachallenge.presentation.components.FavoriteFilterChip
import dev.xget.ualachallenge.presentation.maps.CityMapScreenContent
import dev.xget.ualachallenge.presentation.screens.Screen
import dev.xget.ualachallenge.presentation.ui_data.City
import dev.xget.ualachallenge.presentation.ui_data.mockCities
import kotlinx.coroutines.flow.flowOf

@Composable
fun CitiesBrowserHome(
    viewModel: CitiesBrowserHomeViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state = viewModel.uiState.collectAsState()

    val cities = viewModel.citiesFlow.collectAsLazyPagingItems()
    // Listen for one-time navigation event
    LaunchedEffect(state.value.navToMap) {
        if (state.value.navToMap) {
            state.value.navigateCityId?.let { cityId ->
                navController.navigate(Screen.FullMapsScreen.route + "/${cityId}")
            }
            viewModel.resetNavToMap() // Reset navToMap after navigation
        }
    }

    CitiesBrowserHomeContent(
        cities = cities,
        onQueryChange = viewModel::onQueryChange,
        query = viewModel.searchQuery.collectAsState().value,
        isLoading = state.value.isLoading,
        onFavoriteClick = viewModel::onFavoriteClick,
        filterFavorites = viewModel::onFilterFavoritesChange,
        onlyFavorites = state.value.filterFavorites,
        errorMsg = state.value.errorMsg,
        selectedCity = state.value.selectedCity,
        onCityClick = viewModel::onCityClick,
    )
}

@Composable
fun CitiesBrowserHomeContent(
    cities: LazyPagingItems<City>,
    onQueryChange: (String) -> Unit = {},
    query: String = "",
    isLoading: Boolean = false,
    onFavoriteClick: (Int, Boolean) -> Unit = { _, _ -> },
    filterFavorites: () -> Unit = {},
    onlyFavorites: Boolean = false,
    errorMsg: String? = null,
    //Boolean for landdscape mode
    onCityClick: (City, Boolean) -> Unit = { _, _ -> },
    selectedCity: City? = null,
) {

    val snackBarHostState = SnackbarHostState()
    val isLandscape =
        LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    val screenWidth = LocalConfiguration.current.screenWidthDp
    Scaffold(
        containerColor = Color(0xFFF6F6F6),
        snackbarHost = { SnackbarHost(snackBarHostState) },
    ) {
        it

        LaunchedEffect(errorMsg) {
            if (!errorMsg.isNullOrEmpty()) {
                snackBarHostState.showSnackbar(
                    message = errorMsg,
                    actionLabel = "Dismiss"
                )
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            if (isLoading) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.testTag("LoadingIndicator"))
                }
            } else {
                if (isLandscape) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    ) {
                        // Filters Section
                        Column(
                            modifier = Modifier
                                .width((screenWidth / 2).dp)
                                .fillMaxHeight()
                        ) {
                            CitiesSearchBar(query = query, onQueryChange = onQueryChange)
                            FavoriteFilterChip(
                                isSelected = onlyFavorites,
                                onClick = filterFavorites
                            )
                            CitiesList(cities, onFavoriteClick, onClick = { city -> onCityClick(city, true) })
                        }

                        CityMapScreenContent(
                            cityCoordinates = selectedCity?.coordinates,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxWidth()
                    ) {

                        CitiesList(cities, onFavoriteClick, onClick = { city-> onCityClick(city, false) }, itemScrollable = {
                            CitiesSearchBar(query = query, onQueryChange = onQueryChange)
                            Spacer(modifier = Modifier.height(7.dp))
                            FavoriteFilterChip(
                                isSelected = onlyFavorites,
                                onClick = filterFavorites
                            )
                        })
                    }
                }
            }
        }


    }


}

@Composable
fun CitiesList(
    cities: LazyPagingItems<City>,
    onFavoriteClick: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    onClick: (City) -> Unit = {},
    selectedCityId : Int? = null,
    itemScrollable : @Composable () -> Unit = {},
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .testTag("lazy_column_city")
    ) {
        item {
            itemScrollable()
        }
        items(
            count = cities.itemCount,
            key = cities.itemKey { city -> city.id },
            contentType = cities.itemContentType { "cities" }
        ) { index ->
            val city = cities[index]
            if (city != null) {
                CityCardItem(
                    city = city,
                    onClick = { onClick(city) },
                    onFavoriteClick = { isFavorite -> onFavoriteClick(city.id.toInt(), isFavorite) },
                    isSelected = selectedCityId == city.id.toInt()
                )
            }
        }
    }
}


@Preview
@Composable
fun CitiesBrowserHomePreview() {
    CitiesBrowserHomeContent(
        cities = FakeLazyPagingItems(),
        onQueryChange = {},
        query = "",
        isLoading = false
    )
}

@Composable
fun FakeLazyPagingItems(customCities: List<City> = mockCities): LazyPagingItems<City> {

    val fakePagingData = PagingData.from(customCities)

    val fakePagingFlow = flowOf(fakePagingData)
    return fakePagingFlow.collectAsLazyPagingItems()
}