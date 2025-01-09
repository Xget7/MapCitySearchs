package dev.xget.ualachallenge.presentation.components

import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.xget.ualachallenge.presentation.City
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.xget.ualachallenge.presentation.Coordinates

@Composable
fun CityCardItem(modifier: Modifier = Modifier, city: City, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
            ){
                Text(
                    text = city.name + ", ${city.country}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

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
    CityCardItem(city = city){}
}