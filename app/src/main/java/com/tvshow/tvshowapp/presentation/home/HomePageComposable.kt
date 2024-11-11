package com.tvshow.tvshowapp.presentation.home

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.tvshow.tvshowapp.uielements.HomePageItemComposable

@Composable
fun HomePageComposable(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onItemClicked : (Any) -> Unit,
) {
    val lazyPagingItems = homeViewModel.tvShowPagingData.collectAsLazyPagingItems()
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier
            .padding(10.dp)
    ) {
        items(lazyPagingItems.itemCount) { tvShow ->
            lazyPagingItems[tvShow]?.let { item ->
                HomePageItemComposable(
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(color = Color.LightGray),
                        onClick = {
                            val result: Any = when {
                                item.permaLink.isNotEmpty() -> {
                                    item.permaLink
                                }
                                item.id != 0 -> {
                                    item.id
                                }
                                else -> {
                                    Toast.makeText(context,"No Link Found",Toast.LENGTH_SHORT).show()
                                }
                            }
                            onItemClicked(result)
                        }
                    ),
                    name = item.name,
                    network = item.network,
                    startDate = item.startDate,
                    status = item.status,
                    imageUrl = item.imageThumbnailPath
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

        lazyPagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {

                }

                loadState.append is LoadState.Error -> {
                    item {
                        Text(
                            text = "Failed to load more items",
                            color = Color.Red,
                            modifier = Modifier
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}