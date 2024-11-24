package com.tvshow.tvshowapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.tvshow.myapplication.R
import com.tvshow.tvshowapp.navigation.NavGraph
import com.tvshow.tvshowapp.navigation.Route
import com.tvshow.tvshowapp.ui.theme.TvShowComposeTheme
import com.tvshow.tvshowapp.uielements.header.TvShowHeaderType
import com.tvshow.tvshowapp.uielements.header.TopBarComposable
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SetThemeColor()

            val navController = rememberNavController()
            TvShowComposeTheme {
                Surface(modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                ) {
                    Scaffold(
                        topBar = {
                            when (navController.currentBackStackEntryAsState().value?.destination?.route) {
                                Route.Home.route -> {
                                    TopBarComposable(
                                        tvShowHeaderType = TvShowHeaderType.SIMPLE,
                                        headerTitle = "Tv Shows"
                                    )
                                }

                                Route.Detail.route + "/{detailId}"-> {
                                    TopBarComposable(
                                        tvShowHeaderType = TvShowHeaderType.MULTI,
                                        headerTitle = "Tv Show Detail",
                                        backClick = {
                                            navController.popBackStack()
                                        }
                                    )
                                }
                            }
                        }
                    ) { paddingValues ->
                        Column (
                            modifier = Modifier.padding(paddingValues)
                        ){
                            NavGraph(
                                startDestination = Route.Home.route,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SetThemeColor(){
    val isSystemInDarkMode = isSystemInDarkTheme()
    val systemController = rememberSystemUiController()
    val systemTheme = if (isSystemInDarkMode)
        colorResource(id = R.color.cardBackground)
    else colorResource(id = R.color.cardBackground)
    SideEffect {
        systemController.setSystemBarsColor(
            color = systemTheme,
            darkIcons = !isSystemInDarkMode
        )
    }
}