package com.howtokaise.mynews.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.howtokaise.mynews.domain.navigation.Route

@Composable
fun BottomBar(navController: NavController) {

    val items = listOf(
        NavigationItem( Icons.Default.Home, Route.Home.route),
        NavigationItem( Icons.Default.Person, Route.Liked.route),
    )

    val isDark = isSystemInDarkTheme()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val backgroundColor = if (isDark) Color.Black else Color.White
    val selectedColor = if (isDark) Color.White else Color.Black
    val unselectedColor = Color.Gray

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .height(40.dp)
            .background(backgroundColor),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            val selected = currentRoute == item.route

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable {
                        if (!selected) {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription =null,
                    tint = if (selected) selectedColor else unselectedColor,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}

data class NavigationItem(
    val icon: ImageVector,
    val route: String
)

