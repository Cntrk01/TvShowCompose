package com.tvshow.tvshowapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tvshow.tvshowapp.presentation.home.HomePageComposable

@Composable
fun NavGraph(
    startDestination: String,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ){
        composable(route = Route.Home.route){
            HomePageComposable()
        }

        composable(route = Route.Detail.route){

        }
    }
}