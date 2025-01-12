package com.tvshow.tvshowapp.common.uielements

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.text.TextUtils
import android.view.View
import android.webkit.WebView
import android.widget.TextView
import androidx.compose.animation.animateContentSize
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.tvshow.myapplication.R
import com.tvshow.tvshowapp.core.getColorFromResource
import com.tvshow.tvshowapp.domain.model.attr.TvShowDetailAttr
import com.tvshow.tvshowapp.domain.model.response.Episode
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun TvShowDetailComposable(
    modifier: Modifier = Modifier,
    attribute: TvShowDetailAttr,
) {
    LazyColumn(
        modifier = modifier,
    ){
        item {
            TvShowImageList(pictureList = attribute.imageList)
        }
        item {
            TvShowDescription(attribute = attribute)
        }
        item {
            TvShowInformation(attribute = attribute)
        }
    }
}

@Composable
internal fun TvShowInformation(
    attribute: TvShowDetailAttr
){
    val tabTitles = listOf("Links", "Description", "Episodes")

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { tabTitles.size }
    )

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.Top
    ) {
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

        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            state = pagerState,
            verticalAlignment = Alignment.Top,
            //verticalAlignment vermeyince side effect çıkıyor.Çünkü horizontal pagerin default verticalAligment değeri CenterVertically.
            //Bundandolayı her sayfaya geçince ortalamaya çalışıyor.
            //Sayfalar arası gezerken yukarıda boşluk oluşup düzeliyor.İçeriği bir öncekine göre ayarlıyor
        ) { page ->
            when (page) {
                0 -> TvShowButton(
                    websiteUrl = attribute.url,
                    sourceUrl = attribute.descriptionSource,
                    youtubeUrl = attribute.youtubeLink
                )
                1 -> TvShowExpandableText(text = attribute.description.toString())
                2 -> EpisodesRow(
                    imageUrl = attribute.imageThumbnailPath,
                    episodes = attribute.episodes ?: emptyList()
                )
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun TvShowEpisodeCard(
    modifier: Modifier = Modifier,
    imagePainter: String?,
    episode: Episode,
    onHeightMeasured: (Dp) -> Unit,
    maxHeight: Dp,
) {
    val density = LocalDensity.current

    Card(
        modifier = modifier
            .width(150.dp)
            .heightIn(maxHeight)
            .onGloballyPositioned { coordinates ->
                val height = with(density) { coordinates.size.height.toDp() }
                onHeightMeasured(height)
            },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp)),
                model = imagePainter,
                contentDescription = "Episode Image",
                contentScale = ContentScale.FillBounds,
                placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                error = painterResource(id = R.drawable.notfoundimage),
            )

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

@SuppressLint("UnrememberedMutableState")
@Composable
internal fun EpisodesRow(imageUrl: String?, episodes: List<Episode>) {
    val maxHeight = mutableStateOf(0.dp)

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(episodes.size) { index ->
            TvShowEpisodeCard(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp)),
                imagePainter = imageUrl,
                episode = episodes[index],
                onHeightMeasured = {
                    if (it > maxHeight.value) {
                        maxHeight.value = it
                    }
                },
                maxHeight = maxHeight.value
            )
        }
    }
}

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
            modifier = Modifier.size(300.dp),
            painter = painterResource(id = R.drawable.notfoundimage),
            contentDescription = "Picture",
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
internal fun TvShowDescription(
    attribute: TvShowDetailAttr,
) {
    Column (
        modifier = Modifier
            .padding(10.dp)
    ){
        Text(
            modifier = Modifier
                .align(CenterHorizontally),
            text = attribute.name.toString().uppercase(),
            fontSize = 20.sp,
            maxLines = 1,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
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
                    "Status" to (attribute.status ?: "Not Found"),
                    "Start Date" to (attribute.startDate ?: "Not Found"),
                    "End Date" to (attribute.endDate ?: "Not Found"),
                    "Rating " to (attribute.rating?.toDoubleOrNull() ?: "Not Found"),
                    "Network " to (attribute.network ?: "Not Found"),
                    "Country " to (attribute.country ?: "Not Found")
                ).forEach { (label, value) ->
                    Text(
                        text = buildAnnotatedString {
                            append("$label : ")

                            withStyle(
                                style = SpanStyle(
                                    color = if (label == "Status" && value == "Running")
                                                Color.Green
                                            else
                                                Color.Red.takeIf { label == "Status" }
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
}

@Composable
internal fun TvShowButton(
    websiteUrl: String?,
    sourceUrl: String?,
    youtubeUrl: String?,
) {
    var webUrl by remember { mutableStateOf("") }
    var showWebView by remember { mutableStateOf(false) }

    if (webUrl.isNotEmpty()) {
        WebViewScreen(
            url = webUrl,
            onClose = {
                showWebView = false
                webUrl = ""
            }
        )
    }else{
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            websiteUrl?.let {
                Button(
                    onClick = {
                        webUrl = it
                        showWebView = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(15),
                    colors = ButtonDefaults.buttonColors(getColorFromResource(R.color.websiteButton))
                ) {
                    Text("WEBSITE", color = colorResource(id = R.color.detailButtonColor))
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            sourceUrl?.let {
                Button(
                    onClick = {
                        webUrl = it
                        showWebView = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(15),
                    colors = ButtonDefaults.buttonColors(getColorFromResource(R.color.sourceButton))
                ) {
                    Text("SOURCE", color = colorResource(id = R.color.detailButtonColor))
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            youtubeUrl?.let {
                Button(
                    onClick = {
                        webUrl = it
                        showWebView = true
                    },
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

}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
@Composable
internal fun WebViewScreen(
    url: String,
    onClose: () -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        onDismissRequest = {},
        sheetState = bottomSheetState,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context ->
                    WebView(context).apply {
                        settings.javaScriptEnabled = true

                        loadUrl(url)

                        setOnTouchListener { v, event ->
                            v.parent.requestDisallowInterceptTouchEvent(true)
                            v.onTouchEvent(event)
                            true
                        }
                    }
                }
            )

            IconButton(
                onClick = onClose,
                modifier = Modifier
                    .padding(16.dp)
                    .size(40.dp)
                    .align(Alignment.TopStart)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
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
            .animateContentSize()
    ) {
        AndroidView(
            factory = { context -> TextView(context) },
            update = {
                it.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_COMPACT)
                it.maxLines = if (isExpanded) Int.MAX_VALUE else minimizedMaxLines
                it.ellipsize = if (isExpanded) null else TextUtils.TruncateAt.END
                it.textSize = 16f
                it.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                it.typeface = Typeface.DEFAULT //Fontu buradan değişebiliriz.
                it.setTextColor(ContextCompat.getColor(it.context, R.color.detailDescriptionTextColor))
            },
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