package dev.xget.ualachallenge

import android.content.res.Configuration
import android.util.Log
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dev.xget.ualachallenge.presentation.home.CitiesBrowserHome
import dev.xget.ualachallenge.presentation.home.CitiesBrowserHomeContent
import dev.xget.ualachallenge.presentation.home.FakeLazyPagingItems
import dev.xget.ualachallenge.presentation.ui_data.City
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class HomeScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)


    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()


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


}