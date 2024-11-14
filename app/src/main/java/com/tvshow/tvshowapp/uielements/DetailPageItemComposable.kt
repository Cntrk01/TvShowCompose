package com.tvshow.tvshowapp.uielements

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.tvshow.myapplication.R
import com.tvshow.tvshowapp.domain.model.detail.TvShow
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailPageItemComposable(
    modifier: Modifier = Modifier,
    attribute: TvShow,
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = {
            attribute.pictures?.size ?: 0
        }
    )

    LazyColumn(
        modifier = modifier,
    ) {
        item {
            attribute.pictures?.let { _ ->
                Column(
                    modifier = Modifier
                        .height(350.dp)
                        .fillMaxWidth(),
                ) {
                    Box {
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxSize(),
                            contentPadding = PaddingValues(10.dp)
                        ) { page ->
                            CardContent(
                                image = attribute.pictures,
                                index = page,
                                pagerState = pagerState
                            )
                        }

                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(15.dp)
                                .zIndex(1f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.Bottom,
                        ) {
                            attribute.pictures.forEachIndexed { index, _ ->
                                val color = if (pagerState.currentPage == index) Color.Green else Color.LightGray
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .padding(5.dp)
                                        .clip(CircleShape)
                                        .background(color)
                                )
                            }
                        }
                    }
                }
            } ?: run {
                Image(
                    modifier = Modifier.size(200.dp),
                    painter = painterResource(id = R.drawable.notfoundimage),
                    contentDescription = "Picture",
                    contentScale = ContentScale.Crop,
                )
            }
        }
        item {
            Column(
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = attribute.name.toString(),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                )

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = attribute.startDate.toString() + " - " + (attribute.endDate
                        ?: "Unknown"),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                )

            }
        }
        item {
            Column(
                modifier = Modifier.padding(horizontal = 15.dp)
            ) {
                Spacer(modifier = Modifier.height(15.dp))

                TvShowButton(attribute = attribute)

                Spacer(modifier = Modifier.height(15.dp))

                ExpandableText(text = attribute.description.toString())
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun CardContent(image: List<String>, index: Int, pagerState: PagerState) {
    val pageOffset = ((pagerState.currentPage - index) + pagerState.currentPageOffsetFraction)

    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(2.dp)
            .graphicsLayer {
                lerp(
                    start = 0.70f,
                    stop = 1f,
                    fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f)
                ).also { scale ->
                    scaleX = scale.absoluteValue
                    scaleY = scale.absoluteValue
                }
                alpha = lerp(
                    start = 0.5f,
                    stop = 1f,
                    fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f)
                ).absoluteValue
            },

        ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = ImageRequest.Builder(LocalContext.current)
                .data(image[index])
                .crossfade(true)
                .scale(Scale.FILL)
                .build(),
            error = painterResource(id = R.drawable.notfoundimage),
            contentDescription = "Picture",
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
internal fun TvShowButton(
    attribute: TvShow,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Button(
            onClick = { /* TODO: Birinci butonun işlemi */ },
            modifier = Modifier
                .weight(1f)
                .height(50.dp),
            shape = RoundedCornerShape(15),
            colors = ButtonDefaults.buttonColors(Color.DarkGray)
        ) {
            Text("WEBSITE")
        }

        Button(
            onClick = { /* TODO: İkinci butonun işlemi */ },
            modifier = Modifier
                .weight(1f)
                .height(50.dp),
            shape = RoundedCornerShape(15),
            colors = ButtonDefaults.buttonColors(Color.Blue)
        ) {
            Text("SOURCE") //description source linkine gidicek
        }

        attribute.youtubeLink?.let {
            Button(
                onClick = { /* TODO: Üçüncü butonun işlemi */ },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                shape = RoundedCornerShape(15),
                colors = ButtonDefaults.buttonColors(Color.Red)
            ) {
                Text("YOUTUBE")
            }
        }
    }
}

@Composable
internal fun ExpandableText(
    text: String,
    minimizedMaxLines: Int = 3
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        Text(
            text = text,
            maxLines = if (isExpanded) Int.MAX_VALUE else minimizedMaxLines,
            overflow = TextOverflow.Ellipsis,
            fontSize = 16.sp
        )

        Text(
            modifier = Modifier
                .clickable { isExpanded = !isExpanded },
            text = if (isExpanded) "Read Less" else "Read More...",
            fontSize = 18.sp,
            color = Color.Blue,
        )
    }
}