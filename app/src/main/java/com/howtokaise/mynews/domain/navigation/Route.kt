package com.howtokaise.mynews.domain.navigation

sealed class Route (
    val route: String
){
    object Home : Route("home")
    object Liked : Route("liked")
}