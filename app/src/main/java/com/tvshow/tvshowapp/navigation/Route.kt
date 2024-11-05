package com.tvshow.tvshowapp.navigation

sealed class Route (
    val route: String,
    val topBarTitle: String? = null,
){
    data object Home: Route("home", "Tv Shows")
    data object Detail: Route("detail", "Tv Show Detail")

    companion object {
        val allRoutes = listOf(Home, Detail)
    }
}