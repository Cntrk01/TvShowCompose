package com.tvshow.tvshowapp.navigation

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tvshow.tvshowapp.presentation.detail.DetailPageComposable
import com.tvshow.tvshowapp.presentation.home.HomePageComposable

@Composable
fun NavGraph(
    startDestination: String,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(route = Route.Home.route) {
            HomePageComposable(
                onItemClicked = { linkOrId ->
                    //Saved State Handle key-value(anahtar-değer) depolama mekanizması olarak işlev görür.
                    //Architecture Components ViewModel kütüphanesinde tanıtılan bir sınıftır
                    //Ekran döndme,activity,fragment sonlanma durumlarında veriyi tutmaya devam eder.
                    //!Veri tutarken boyutunu dikkate almalıyız çünkü büyük verilerle işlem yapıyorsak bize performans sorunlarına sebebiyet verebilir
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = "detailId",
                        value = linkOrId
                    )
                    navController.navigate(route = Route.Detail.createRoute(linkOrId))
                }
            )
        }

        composable(route = Route.Detail.route,
            enterTransition = {
                slideInHorizontally (
                    initialOffsetX = {-it},
                    animationSpec = tween(
                        200,
                        easing = FastOutLinearInEasing
                    )
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(
                        200,
                        easing = FastOutLinearInEasing
                    )
                )
            }
        ) {
            DetailPageComposable()
        }
    }
}