package com.tvshow.tvshowapp.navigation

import com.tvshow.tvshowapp.uielements.header.TopBarConfig
import com.tvshow.tvshowapp.uielements.header.TvShowHeaderType

sealed class Route(val route: String, val topBarConfig: TopBarConfig? = null) {
    companion object{
        val allRoutes = listOf(Home,Detail)
    }

    data object Home : Route(
        route = "home",
        topBarConfig = TopBarConfig(
            headerType = TvShowHeaderType.SIMPLE,
            title = "Tv Shows"
        )
    )
    data object Detail : Route(
        route = "detail/{detailId}",
        topBarConfig = TopBarConfig(
            headerType = TvShowHeaderType.MULTI,
            title = "Tv Show Detail",
        )
    ){
        fun createRoute(detailId: Any) = "detail/$detailId"
    }
}

