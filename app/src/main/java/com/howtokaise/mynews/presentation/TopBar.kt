package com.howtokaise.mynews.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TopBar(
    viewModel: NewViewModel,
    selectedCategory : String,
    onCategorySelected : (String) -> Unit
) {
    val categories = listOf(

        "General",
        "Technology",
        "Health",
        "Science",
        "Sports",
        "Entertainment",
        "Business",
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, start = 8.dp, end = 4.dp)
            .height(35.dp)
            .horizontalScroll(rememberScrollState())
    ) {
        categories.forEach { category ->
            Text(
                text = category,
                fontWeight = FontWeight.Bold,
                color = if (selectedCategory == category) Color.Magenta else Color.Gray,
                modifier = Modifier
                    .padding(end = 18.dp)
                    .clickable {
                        onCategorySelected(category)
                    }
            )
        }
    }

}