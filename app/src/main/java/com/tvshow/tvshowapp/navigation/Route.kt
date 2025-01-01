package com.tvshow.tvshowapp.navigation

import androidx.compose.runtime.mutableStateOf
import com.tvshow.tvshowapp.common.uielements.header.TopBarConfig
import com.tvshow.tvshowapp.common.uielements.header.TvShowHeaderType

sealed class Route(
    val route: String,
    val topBarConfig: TopBarConfig? = null
) {
    companion object{
        val allRoutes = listOf(Home,Detail)
    }

    open fun getTitle(): String {
        return topBarConfig?.title ?: ""
    }

    data object Home : Route(
        route = "home",
        topBarConfig = TopBarConfig(
            headerType = TvShowHeaderType.SIMPLE,
            title = "Tv Shows"
        )
    ){
        override fun getTitle(): String {
            return "Tv Shows Override"
        }
    }

    //data object Singleton olduğu için her zaman aynı referans üzerinden işlem yapılır.
    data object Detail : Route(
        route = "detail/{detailId}",
        topBarConfig = TopBarConfig(
            headerType = TvShowHeaderType.MULTI,
            title = "Loading...",
        )
    ){
        private var dynamicTitle = mutableStateOf("")

        fun createRoute(detailId: Any) = "detail/$detailId"

        fun updateTitle(newTitle : String){
            dynamicTitle.value = newTitle
        }

        override fun getTitle(): String {
            return dynamicTitle.value.ifEmpty {
                topBarConfig?.title ?: "Title Is Empty"
            }
        }
    }
}