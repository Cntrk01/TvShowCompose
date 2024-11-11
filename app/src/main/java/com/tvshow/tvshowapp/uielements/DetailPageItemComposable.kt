package com.tvshow.tvshowapp.uielements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tvshow.myapplication.R
import com.tvshow.tvshowapp.domain.model.detail.TvShow

@Composable
fun DetailPageItemComposable(
    modifier: Modifier = Modifier,
    attribute: TvShow,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        item {
            attribute.pictures?.let { pictureSize ->
                repeat(pictureSize.size) {
                    val picture = attribute.pictures[it]
                    Box(
                        modifier = Modifier.size(200.dp)
                    ) {
                        AsyncImage(
                            modifier = Modifier.fillMaxSize(),
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(picture)
                                .crossfade(true)
                                .build(),
                            error = painterResource(id = R.drawable.notfoundimage),
                            contentDescription = "Picture",
                            contentScale = ContentScale.Crop,
                        )

                        Row(
                            modifier = Modifier
                                .size(25.dp)
                                .align(Alignment.BottomCenter)
                        ) {
                            repeat(attribute.pictures.size) { size ->
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(CircleShape)
                                        .background(if (it == size) Color.Green else Color.LightGray),
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
            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier.padding(horizontal = 40.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = attribute.name.toString(),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    attribute.endDate
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = { /* TODO: Birinci butonun işlemi */ },
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.LightGray)
                        .height(50.dp),
                    shape = RoundedCornerShape(15)
                ) {
                    Text("WEBSITE")
                }

                Button(
                    onClick = { /* TODO: İkinci butonun işlemi */ },
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.Blue)
                        .height(50.dp),
                    shape = RoundedCornerShape(15)
                ) {
                    Text("SOURCE") //description source linkine gidicek
                }

                attribute.youtubeLink?.let {
                    Button(
                        onClick = { /* TODO: Üçüncü butonun işlemi */ },
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.Red)
                            .height(50.dp),
                        shape = RoundedCornerShape(15)
                    ) {
                        Text("YOUTUBE") //youtube linkine gidicek
                    }
                }
            }
        }
    }
}