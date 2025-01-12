package dev.xget.ualachallenge.presentation.components

import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.xget.ualachallenge.presentation.ui_data.City
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.xget.ualachallenge.presentation.ui_data.Coordinates

@Composable
fun CityCardItem(
    city: City,
    onClick: () -> Unit,
    onFavoriteClick: (Boolean) -> Unit,
    isSelected: Boolean = false,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .testTag("city_card"),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = city.name + ", ${city.country}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                IconButton(
                    onClick = { onFavoriteClick(!city.isFavorite) }
                ) {
                    Icon(
                        imageVector = if (city.isFavorite) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (city.isFavorite) Color.Red else Color.LightGray,
                        modifier = Modifier.size(30.dp)
                    )
                }

            }

            if (city.coordinates != null) {
                Text(
                    text = "Coordinates: Lat ${city.coordinates.latitude}, Lon ${city.coordinates.longitude}",
                    fontSize = 16.sp,
                    color = Color.DarkGray
                )
            }
            Text(
                text = "ID: ${city.id}",
                fontSize = 16.sp,
                color = Color.DarkGray
            )
        }
    }

}

@Preview
@Composable
fun PreviewCityCard() {
    val city = City(
        country = "UA",
        name = "Alupka",
        id = "713514",
        coordinates = Coordinates(latitude = 44.416668, longitude = 34.049999)
    )
    CityCardItem(city = city, onFavoriteClick = {}, onClick = {})
}