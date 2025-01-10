package dev.xget.ualachallenge.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.xget.ualachallenge.ui.theme.Blue40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitiesSearchBar(
    modifier: Modifier = Modifier,
    query: String = "",
    onQueryChange: (String) -> Unit = {},
    active: Boolean = false,
    onActiveChange: (Boolean) -> Unit = {},
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {},
        active = active,
        onActiveChange = onActiveChange,
        modifier = modifier.fillMaxWidth().padding(horizontal = 18.dp, vertical = 2.dp),
        colors = SearchBarDefaults.colors(
            containerColor = Color.White,
            inputFieldColors = SearchBarDefaults.inputFieldColors(
                focusedTextColor = Color.Black,
                cursorColor = Color.Black,
            ),
        ),
        placeholder = {
            Text(
                text = "Search cities",
                color = Color.Gray,
                fontSize = 16.sp,
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.Gray
            )
        },
        trailingIcon ={
            //clear button
            if (query.isNotEmpty()) {
                IconButton(onClick = {
                    onQueryChange("")
                }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear",
                        tint = Blue40
                    )
                }
            }
        }


    ) {}


}


@Preview
@Composable
private fun CitiesSearchBarPreview() {

}
