package com.tvshow.tvshowapp.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DetailPageComposable(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel(),
){
    val lifeCycle = LocalLifecycleOwner.current

    viewModel.tvShow.observe(lifeCycle) { detailPageState ->
        when {
            detailPageState.loading -> {
                println("Loading data...")
            }
            detailPageState.error.isNotEmpty() -> {
                println("Error: ${detailPageState.error}")
            }
            detailPageState.tvShow != null -> {
                println("TvShow data: ${detailPageState.tvShow}")
            }
            else -> {
                println("No data received.")
            }
        }
    }

    Column(
        modifier = modifier,
    ) {
        //DetailPageItemComposable(attribute = )
    }
}