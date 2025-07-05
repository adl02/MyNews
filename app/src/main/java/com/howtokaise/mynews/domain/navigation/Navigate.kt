package com.howtokaise.mynews.domain.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.howtokaise.mynews.presentation.NewViewModel
import com.howtokaise.mynews.presentation.HomeScreen
import com.howtokaise.mynews.presentation.LikedScreen

@Composable
fun Navigate(
    navController: NavHostController,
    viewModel: NewViewModel,
    scrollToTopTrigger : MutableState<Boolean>,
    refreshTrigger : MutableState<Boolean>
) {
    NavHost(
        navController = navController,
        startDestination = Route.Home.route
    ) {
        composable(Route.Home.route) {
            HomeScreen(
                viewModel,
                scrollToTopTrigger,
                refreshTrigger,

            )
        }

        composable(Route.Liked.route) {
            LikedScreen(viewModel)
        }
    }
}