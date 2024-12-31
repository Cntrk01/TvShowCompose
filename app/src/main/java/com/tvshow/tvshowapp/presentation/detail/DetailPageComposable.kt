package com.tvshow.tvshowapp.presentation.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tvshow.tvshowapp.navigation.Route
import com.tvshow.tvshowapp.common.uielements.TvShowDetailComposable
import com.tvshow.tvshowapp.common.uielements.TvShowLoadingComposable

@Composable
fun DetailPageComposable(
    modifier: Modifier = Modifier,
){
    val viewModel : DetailViewModel = hiltViewModel()
    val detailItem by viewModel.tvShow.collectAsState()

    Column(
        modifier = modifier
    ) {
        when {
            detailItem.loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    TvShowLoadingComposable()
                }
            }
            detailItem.error.isNotEmpty() -> {
                Route.Detail.updateTitle(newTitle = "Error")

                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        Text(
                            text = detailItem.error,
                            textAlign = TextAlign.Center
                        )
                        if (detailItem.isShowAction){
                            Spacer(modifier = Modifier.height(10.dp))

                            Button(
                                modifier = Modifier.align(CenterHorizontally),
                                onClick = viewModel::retryGetTvShow
                            ) {
                                Text(text = "Retry")
                            }
                        }
                    }
                }
            }

            detailItem.tvShow != null -> {
                Route.Detail.updateTitle(newTitle = detailItem.tvShow?.name ?: "")
                TvShowDetailComposable(attribute = detailItem.tvShow!!)
            }
        }
    }
}