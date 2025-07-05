package com.howtokaise.mynews.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.howtokaise.mynews.domain.navigation.Route

@Composable
fun BottomBar(
    navController: NavController,
    onHomeReselected: () -> Unit
) {

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
                        } else if (item.route == Route.Home.route){
                            onHomeReselected()
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

