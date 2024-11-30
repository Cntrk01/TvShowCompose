package com.tvshow.tvshowapp.uielements.header

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.tvshow.tvshowapp.navigation.Route

@Composable
fun TvShowTopBar(
    navController: NavController,
){
   val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
   //Burda biz currentRoutedan gelen routu kontrol ediyoruz.startsWith, bir string'in başka bir string ile başlayıp başlamadığını kontrol ediyorum
   val topBarConfig = Route.allRoutes.find { currentRoute?.startsWith(it.route) == true }?.topBarConfig

   topBarConfig?.let { config ->
       TopBarComposable(
           tvShowHeaderType = config.headerType,
           headerTitle = config.title,
           backClick =  {
               navController.popBackStack()
           }
       )
   }
}