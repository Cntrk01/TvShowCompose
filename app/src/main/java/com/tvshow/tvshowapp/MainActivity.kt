package com.tvshow.tvshowapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.tvshow.tvshowapp.navigation.NavGraph
import com.tvshow.tvshowapp.navigation.Route
import com.tvshow.tvshowapp.presentation.home.HomePageComposable
import com.tvshow.tvshowapp.ui.theme.TvShowComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            TvShowComposeTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Scaffold (
                        topBar = {
                            TopAppBar(
                                title = { Text("Tv Show App") }
                            )
                        }
                    ){ paddingValues ->
                        Column (
                            modifier = Modifier.padding(paddingValues)
                        ){
                            NavGraph(
                                startDestination =Route.Home.route ,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}