package com.tvshow.tvshowapp.navigation

sealed class Route (
    val route: String,
){
    data object Home: Route(route = "home")
    data object Detail: Route(route = "detail")
}