package dev.xget.ualachallenge.presentation.maps

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import dev.xget.ualachallenge.presentation.ui_data.Coordinates
import dev.xget.ualachallenge.ui.theme.Blue40


@Composable
fun CityMapScreen(
    modifier: Modifier = Modifier,
    viewModel: CityMapScreenViewModel = hiltViewModel(),
    navController: NavController,
) {

    val cityCoordinates by viewModel.cityCoordinates.collectAsState(initial = null)


    Scaffold(
        topBar = {
            //back handling
            Row(
                modifier = Modifier.padding(top = 30.dp).fillMaxWidth(),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {

                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Back",
                        tint = Blue40,
                    )
                }
                Text(
                    text = "Back",
                    color = Color.DarkGray
                )
            }
        }
    ) {
        it

        CityMapScreenContent(
            modifier = modifier.padding(it),
            cityCoordinates = cityCoordinates
        )
    }

}

@Composable
fun CityMapScreenContent(
    modifier: Modifier = Modifier,
    cityCoordinates: Coordinates?,
) {
    val defaultCameraPosition = CameraPosition(
        LatLng(-34.6157129, -58.5981081),
        15f,
        0f,
        0f
    )
    GoogleMap(
        modifier = modifier,
        cameraPositionState = cityCoordinates?.toCameraPositionState() ?: CameraPositionState(
            defaultCameraPosition
        ),
    ) {
        Marker(
            state = MarkerState(
                position = cityCoordinates?.toLatLng() ?: LatLng(-34.6157129, -58.5981081),
            ),
        )
    }


}