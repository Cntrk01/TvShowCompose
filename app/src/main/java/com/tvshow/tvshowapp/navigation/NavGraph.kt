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
                    //currentBackStackEntry  Şu anda aktif olan ekranın verilerine erişmek için kullanılır.
                    //Örneğin, dinamik rota parametreleri veya SavedStateHandle'daki mevcut verileri almak için kullanabiliriz.
                    //val currentEntry = navController.currentBackStackEntry
                    //val detailId = currentEntry?.savedStateHandle?.get<String>("detailId")

                    //Saved State Handle key-value(anahtar-değer) depolama mekanizması olarak işlev görür.
                    //Architecture Components ViewModel kütüphanesinde tanıtılan bir sınıftır
                    //Ekran döndme,activity,fragment sonlanma durumlarında veriyi tutmaya devam eder.
                    //!Veri tutarken boyutunu dikkate almalıyız çünkü büyük verilerle işlem yapıyorsak bize performans sorunlarına sebebiyet verebilir
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = "detailId",
                        value = linkOrId
                    )

                    //previousBackStackEntry Kullanımı
                    //Aktif ekrandan bir önceki ekrana ait verileri almak için kullanılır.
                    //Örneğin, bir önceki ekrandan bir veri aktarıldıysa ve bu veri hala savedStateHandle'da bulunuyorsa kullanabiliriz
                    //val previousEntry = navController.previousBackStackEntry
                    //val previousData = previousEntry?.savedStateHandle?.get<String>("key")

                    // Burada dinamik rota parametrelerine dayanır.(navControllerin savedStatesinde buna ihtiyaç yok.Doğrudan key value ile gönderilir.Composable içerisinde  navController.previousBackStackEntry?.savedStateHandle?.get<>() diyerek alırız.)
                    // Eğer rota parametresi (detail/{detailId}) tanımlamazsan,
                    // Jetpack Navigation bu parametreyi ViewModel'in SavedStateHandle'ına aktaramaz.
                    // Bundan ötürü createRoute methodunu yazdım
                    navController.navigate(route = Route.Detail.createRoute(detailId = linkOrId)) ////örneğin route burda detail/123 oluyor.Viewmodelde de 123 değerini alıyorum.
                }
            )
        }

        composable(route = Route.Detail.route,
            enterTransition = {
                slideInHorizontally (
                    initialOffsetX = {-it},
                    animationSpec = tween(
                        500,
                        easing = FastOutLinearInEasing
                    )
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(
                        500,
                        easing = FastOutLinearInEasing
                    )
                )
            }
        ) {
            DetailPageComposable()
        }
    }
}