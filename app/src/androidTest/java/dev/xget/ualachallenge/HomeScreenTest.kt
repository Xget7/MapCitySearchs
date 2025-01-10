package dev.xget.ualachallenge

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.testing.HiltAndroidRule
import dev.xget.ualachallenge.presentation.home.CitiesBrowserHome
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {


    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)


    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()


    @Test
    fun myTest() {
        // Start the app
        composeTestRule.setContent {
            val navController = rememberNavController()
            CitiesBrowserHome(
                navController = navController
            )
        }


        composeTestRule.onNodeWithText("Coordinates").assertIsDisplayed()
    }

}