package com.tvshow.tvshowapp.uielements

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.tvshow.myapplication.R
import com.tvshow.tvshowapp.domain.model.detail.Episode
import com.tvshow.tvshowapp.domain.model.detail.TvShow
import com.tvshow.tvshowapp.domain.model.detail.TvShowDescription
import com.tvshow.tvshowapp.domain.model.detail.toTvShowDescription
import com.tvshow.tvshowapp.uielements.UiElementsExt.getColorFromResource
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailPageItemComposable(
    modifier: Modifier = Modifier,
    attribute: TvShow,
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 10.dp),
    ) {
        item {
            TvShowImageList(pictureList = attribute.pictures)
        }
        item {
            TvShowDescription(attribute = attribute.toTvShowDescription())
        }
        item {
            Spacer(modifier = Modifier.height(10.dp))

            val tabTitles = listOf("Links", "Description", "Episodes")
            val pagerState = rememberPagerState(
                initialPage = 0,
                pageCount = {
                    tabTitles.size
                }
            )
            val coroutineScope = rememberCoroutineScope()

            Column(modifier = Modifier) {
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    tabTitles.forEachIndexed { index, title ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            text = { Text(title) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.wrapContentSize()
                ) { page ->
                    when (page) {
                        0 -> TvShowButton(attribute = attribute)
                        1 -> TvShowExpandableText(text = attribute.description.toString())
                        2 -> EpisodesRow(
                            imageUrl = attribute.imageThumbnailPath,
                            episodes = attribute.episodes ?: emptyList()
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun EpisodeCard(modifier: Modifier = Modifier, imagePainter: Painter?, episode: Episode) {
    val maxHeight = mutableStateOf(0.dp)
    val density =LocalDensity.current

    Card(
        modifier = modifier
            .width(150.dp)
            .heightIn(maxHeight.value),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        // Can't represent a size of 425457 in Constraints . Carda onGloballyPositioned verince bu hatayı aldım.
        Column(
            modifier = Modifier.fillMaxSize() .onGloballyPositioned {coordinates ->
                val heightInDp = with(density) {
                    coordinates.size.height.toDp()
                }

                if (heightInDp > maxHeight.value) {
                    maxHeight.value = heightInDp
                }
            },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = imagePainter ?: painterResource(id = R.drawable.notfoundimage),
                contentDescription = "Episode Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentScale = ContentScale.FillBounds
            )
            // Metin Kısmı
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Season ${episode.season}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Episode ${episode.episode}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = episode.name,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Air Date: ${episode.airDate}",
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun EpisodesRow(imageUrl: String?, episodes: List<Episode>) {
    val painter: Painter = rememberAsyncImagePainter(model = imageUrl)

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(episodes.size) { index ->
            EpisodeCard(modifier = Modifier,
                imagePainter = painter,
                episode = episodes[index],
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun TvShowImageList(pictureList: List<String>? = null) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = {
            pictureList?.size ?: 0
        }
    )

    pictureList?.let { imageList ->
        Column(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth(),
        ) {
            Box {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(10.dp)
                ) { page ->
                    TvShowImagePager(
                        image = imageList,
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
                    imageList.forEachIndexed { index, _ ->
                        val color =
                            if (pagerState.currentPage == index) Color.Green else Color.LightGray
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

@Composable
internal fun TvShowDescription(
    attribute: TvShowDescription,
) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        AsyncImage(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .clip(RoundedCornerShape(10.dp)) //yükseklik genişlikten sonra verince uygulamıyor !
                .width(100.dp)
                .height(150.dp),
            model = attribute.imageThumbnailPath,
            contentDescription = "Image"
        )

        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            listOf(
                "Name" to (attribute.name ?: "Not Found"),
                "Status" to (attribute.status ?: "Not Found"),
                "Start Date" to (attribute.startDate ?: "Not Found"),
                "End Date" to (attribute.endDate ?: "Not Found"),
                "Rating " to (attribute.rating?.toDoubleOrNull() ?: "Not Found"),
                "Network " to (attribute.network ?: "Not Found")
            ).forEach { (label, value) ->
                Text(
                    text = buildAnnotatedString {
                        append("$label : ")

                        withStyle(
                            style = SpanStyle(
                                color = if (label == "Status" && value == "Running") Color.Green else Color.Red.takeIf { label == "Status" }
                                    ?: Color.Unspecified
                            )
                        ) {
                            append(value.toString())
                        }
                    },
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun TvShowImagePager(
    image: List<String>,
    index: Int,
    pagerState: PagerState
) {
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
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {


        Button(
            onClick = { /* TODO: Birinci butonun işlemi */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(15),
            colors = ButtonDefaults.buttonColors(getColorFromResource(R.color.websiteButton))
        ) {
            Text("WEBSITE", color = colorResource(id = R.color.detailButtonColor))
        }

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = { /* TODO: İkinci butonun işlemi */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(15),
            colors = ButtonDefaults.buttonColors(getColorFromResource(R.color.sourceButton))
        ) {
            Text(
                "SOURCE",
                color = colorResource(id = R.color.detailButtonColor),
            ) //description source linkine gidicek
        }

        Spacer(modifier = Modifier.height(15.dp))

        attribute.youtubeLink?.let {
            Button(
                onClick = { /* TODO: Üçüncü butonun işlemi */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(15),
                colors = ButtonDefaults.buttonColors(getColorFromResource(R.color.youtubeButton))
            ) {
                Text("YOUTUBE", color = colorResource(id = R.color.detailButtonColor))
            }
        }
    }
}

@Composable
internal fun TvShowExpandableText(
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