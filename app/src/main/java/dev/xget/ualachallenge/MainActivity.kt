package dev.xget.ualachallenge


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import dev.xget.ualachallenge.presentation.home.CitiesBrowserHome
import dev.xget.ualachallenge.presentation.home.CitiesBrowserHomeViewModel
import dev.xget.ualachallenge.presentation.maps.CityMapScreen
import dev.xget.ualachallenge.presentation.maps.CityMapScreenContent
import dev.xget.ualachallenge.presentation.screens.Screen
import dev.xget.ualachallenge.ui.theme.UalaChallengeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UalaChallengeTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.Home.route) {
                    composable(Screen.Home.route) {
                        CitiesBrowserHome(navController = navController)
                    }
                    composable(
                        route = Screen.FullMapsScreen.route + "/{cityId}",
                        arguments = listOf(navArgument("cityId") { type = NavType.StringType })
                    ) {
                        CityMapScreen(
                            navController = navController,
                            modifier = Modifier,
                        )
                    }

                }
            }
        }
    }
}