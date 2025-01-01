package com.tvshow.tvshowapp.presentation.home

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.tvshow.tvshowapp.common.uielements.TvShowErrorComposable
import com.tvshow.tvshowapp.common.uielements.TvShowHomeComposable
import com.tvshow.tvshowapp.common.uielements.TvShowLoadingComposable
import com.tvshow.tvshowapp.core.errorMessage
import com.tvshow.tvshowapp.core.isRetryAvailable

@Composable
fun HomePageComposable(
    modifier: Modifier = Modifier,
    homeViewModel : HomeViewModel = hiltViewModel(),
    onItemClicked : (Any) -> Unit,
) {
    val lazyPagingItems = homeViewModel.tvShowPagingData.collectAsLazyPagingItems()
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier
            .padding(horizontal = 10.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        items(lazyPagingItems.itemCount) { tvShow ->
            lazyPagingItems[tvShow]?.let { item ->
                TvShowHomeComposable(
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
                    country = item.country,
                    status = item.status,
                    imageUrl = item.imageThumbnailPath
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

        lazyPagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            TvShowLoadingComposable()
                        }
                    }
                }

                //Sayfalama işlemine başlandığında.
                //Veri kaynağı (örneğin, bir API veya veri tabanı) sıfırdan yüklenirken.
                loadState.refresh is LoadState.Error -> {
                    val error = loadState.refresh as LoadState.Error
                    val errorMessage = error.errorMessage()
                    val isRetryAvailable = error.isRetryAvailable()

                    item {
                        TvShowErrorComposable(
                            errorMessage = errorMessage,
                            isRetryAvailable = isRetryAvailable,
                            retryAction = lazyPagingItems::retry
                        )
                    }
                }
                //Kullanıcı listeyi yukarı kaydırarak önceki öğelere ulaşmaya çalıştığında tetiklenir.
                //Bir kullanıcı listenin ilk öğesine ulaşıp daha eski verileri yüklemek istediğinde bir hata olursa, bu prepend durumunda görülür.
                loadState.prepend is LoadState.Error -> {
                    val error = loadState.prepend as LoadState.Error
                    val errorMessage = error.errorMessage()
                    val isRetryAvailable = error.isRetryAvailable()

                    item {
                        TvShowErrorComposable(
                            errorMessage = errorMessage,
                            isRetryAvailable = isRetryAvailable,
                            retryAction = lazyPagingItems::retry
                        )
                    }
                }
                //Bir kullanıcı 20. öğeyi kaydırdıktan sonra, 21-40. öğeleri yükleme sırasında bir hata olursa,
                //bu append durumunda yer alır.
                loadState.append is LoadState.Error -> {
                    val error = loadState.append as LoadState.Error
                    val errorMessage = error.errorMessage()
                    val isRetryAvailable = error.isRetryAvailable()

                    item {
                        TvShowErrorComposable(
                            errorMessage = errorMessage,
                            isRetryAvailable = isRetryAvailable,
                            retryAction = lazyPagingItems::retry
                        )
                    }
                }
            }
        }
    }
}