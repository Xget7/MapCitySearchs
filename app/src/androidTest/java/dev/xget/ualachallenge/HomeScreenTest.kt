package dev.xget.ualachallenge

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dev.xget.ualachallenge.data.cities.dto.CityDto
import dev.xget.ualachallenge.local.FakeCitiesLocalDataSource
import dev.xget.ualachallenge.presentation.home.CitiesBrowserHome
import dev.xget.ualachallenge.presentation.home.CitiesBrowserHomeContent
import dev.xget.ualachallenge.presentation.home.CitiesBrowserHomeViewModel
import dev.xget.ualachallenge.presentation.home.FakeLazyPagingItems
import dev.xget.ualachallenge.presentation.maps.CityMapScreen
import dev.xget.ualachallenge.presentation.maps.CityMapScreenContent
import dev.xget.ualachallenge.presentation.maps.CityMapScreenViewModel
import dev.xget.ualachallenge.presentation.screens.Screen
import dev.xget.ualachallenge.presentation.ui_data.City
import dev.xget.ualachallenge.presentation.ui_data.Coordinates
import dev.xget.ualachallenge.remote.FakeCitiesRemoteDataSource
import dev.xget.ualachallenge.repositories.cities.CitiesRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class HomeScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)


    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    private lateinit var citiesLocalDataSource: FakeCitiesLocalDataSource
    private lateinit var citiesRemoteDataSource: FakeCitiesRemoteDataSource

    private lateinit var citiesRepository: CitiesRepository
    lateinit var navController: TestNavHostController

    @Before
    fun setup() {
        // Initialize fake data sources
        citiesLocalDataSource = FakeCitiesLocalDataSource()
        citiesRemoteDataSource = FakeCitiesRemoteDataSource()

        // Initialize the repository using the fake data sources
        citiesRepository = CitiesRepository(
            citiesLocalDataSource = citiesLocalDataSource,
            citiesRemoteDataSource = citiesRemoteDataSource
        )

        // Inject any required dependencies into the test
        hiltRule.inject()

    }


    @Test
    fun citiesBrowserHomeContent_displaysCitiesAndHandlesClick() {
        // Given mock data for cities
        val mockCities = listOf(
            City(id = "1", name = "Alabama", country = "US", isFavorite = false),
            City(id = "2", name = "Albuquerque", country = "US", isFavorite = true)
        )

        // Set up a flag to verify city click
        var clickedCity: City? = null

        // When the composable is rendered
        composeTestRule.setContent {

            val landscapeConfiguration = LocalConfiguration.current.apply {
                orientation = Configuration.ORIENTATION_LANDSCAPE
            }

            println(mockCities)
            val pagingItems = FakeLazyPagingItems(mockCities)

            CompositionLocalProvider(LocalConfiguration provides landscapeConfiguration) {
                CitiesBrowserHomeContent(
                    cities = pagingItems,
                    onQueryChange = {},
                    query = "",
                    isLoading = false,
                    onFavoriteClick = { _, _ -> },
                    filterFavorites = {},
                    onlyFavorites = false,
                    errorMsg = null,
                    onCityClick = { city, _ -> clickedCity = city },
                    selectedCity = null,
                )
            }

        }

        composeTestRule.onNodeWithTag("lazy_column_city").assertExists()

        // Simulate a click on a city
        val nodes = composeTestRule.onAllNodesWithTag("city_card")
        print("Nodes: $nodes , count: ${nodes.printToLog("City Cards")}")
        nodes[0].performClick()
        // Verify the city was clicked
        assertEquals("Alabama", clickedCity?.name)
    }

    @Test
    fun citiesBrowserHomeContent_showsLoadingIndicator() {
        // When the composable is rendered in a loading state
        composeTestRule.setContent {
            val pagingItems = FakeLazyPagingItems(emptyList())

            CitiesBrowserHomeContent(
                cities = pagingItems,
                onQueryChange = {},
                query = "",
                isLoading = true,
                onFavoriteClick = { _, _ -> },
                filterFavorites = {},
                onlyFavorites = false,
                errorMsg = null,
                onCityClick = { _, _ -> },
                selectedCity = null,
            )
        }

        // Then verify the loading indicator is shown
        composeTestRule.onNodeWithTag("LoadingIndicator").assertExists()
    }

    @Test
    fun citiesBrowserHomeContent_showsErrorSnackbar() {
        // When the composable is rendered with an error message
        composeTestRule.setContent {
            val pagingItems = FakeLazyPagingItems(emptyList())
            CitiesBrowserHomeContent(
                cities = pagingItems,
                onQueryChange = {},
                query = "",
                isLoading = false,
                onFavoriteClick = { _, _ -> },
                filterFavorites = {},
                onlyFavorites = false,
                errorMsg = "An error occurred",
                onCityClick = { _, _ -> },
                selectedCity = null,
            )
        }

        // Then verify the snackbar displays the error message
        composeTestRule.onNodeWithText("An error occurred").assertExists()
    }

    @Test
    fun citiesBrowserHome_navigateToCityDetail() = runTest {
        // Given a list of cities
        val mockCities = listOf(
            CityDto(id = "1", name = "Alabama", country = "US")
        )
        citiesLocalDataSource.saveCities(mockCities)


        val mockViewModel = CitiesBrowserHomeViewModel(
            citiesRepository = citiesRepository
        )
        val context = ApplicationProvider.getApplicationContext<Context>()



        // When the composable is rendered
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            NavHost(navController = navController, startDestination = Screen.Home.route) {
                composable(Screen.Home.route) {
                    CitiesBrowserHome(navController = navController, viewModel = mockViewModel)
                }
                composable(
                    route = Screen.FullMapsScreen.route + "/{cityId}",
                    arguments = listOf(navArgument("cityId") { type = NavType.StringType })
                ) {
                   CityMapScreen(
                        navController = navController,
                        modifier = Modifier,
                        viewModel = CityMapScreenViewModel(citiesRepository = citiesRepository, savedStateHandle = SavedStateHandle())
                    )
                }
            }


        }

        // Simulate clicking on a city card

        composeTestRule.onNodeWithTag("city_card").performClick()

        composeTestRule.waitUntil {
            composeTestRule.onNodeWithTag("map_component").isDisplayed()
        }
        navController.assertCurrentRouteName(Screen.FullMapsScreen.route + "/{cityId}")
    }


}
// Can be on a utils file
fun NavController.assertCurrentRouteName(expectedRouteName: String) {
    Assert.assertEquals(expectedRouteName, currentBackStackEntry?.destination?.route)
}