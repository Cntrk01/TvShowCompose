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
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.tvshow.tvshowapp.uielements.TvShowDetailComposable
import com.tvshow.tvshowapp.uielements.TvShowLoadingComposable

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
                    TvShowLoadingComposable()
                }
            }
            detailItem?.error?.isNotEmpty() == true -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Error: ${detailItem?.error}",
                        textAlign = TextAlign.Center
                    )
                }
            }
            detailItem?.tvShow != null -> {
                TvShowDetailComposable(attribute = detailItem?.tvShow!!)
            }
            else -> {
                Text(text = "No data received.")
            }
        }
    }
}