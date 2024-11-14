package com.tvshow.tvshowapp.presentation.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.tvshow.tvshowapp.uielements.DetailPageItemComposable
import com.tvshow.tvshowapp.uielements.LoadingComposable

@Composable
fun DetailPageComposable(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel(),
){
    val detailItem by viewModel.tvShow.observeAsState()

    Column(
        modifier = modifier
    ) {
        when {
            detailItem?.loading == true -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingComposable()
                }
            }
            detailItem?.error?.isNotEmpty() == true -> {
                Text(text = "Error: ${detailItem?.error}")
            }
            detailItem?.tvShow != null -> {
                DetailPageItemComposable(attribute = detailItem?.tvShow!!)
            }
            else -> {
                Text(text = "No data received.")
            }
        }
    }
}