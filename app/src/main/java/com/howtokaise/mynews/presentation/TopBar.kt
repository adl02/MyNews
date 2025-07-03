package com.howtokaise.mynews.presentation

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TopBar() {

    val scrollState = rememberScrollState()


    Row (
        modifier = Modifier
            .padding(top = 5.dp, start = 8.dp, end = 8.dp)
            .height(35.dp)
            .horizontalScroll(scrollState)
    ){
        Text(text = "Trump", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(15.dp))
        Text(text = "Trump", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(15.dp))
        Text(text = "Trump", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(15.dp))
        Text(text = "Trump", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(15.dp))
        Text(text = "Trump", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(15.dp))
        Text(text = "Trump", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(15.dp))
        Text(text = "Trump", fontWeight = FontWeight.Bold)
    }

}