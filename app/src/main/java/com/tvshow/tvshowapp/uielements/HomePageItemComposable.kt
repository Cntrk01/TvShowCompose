package com.tvshow.tvshowapp.uielements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tvshow.myapplication.R

@Composable
fun HomePageItemComposable(
    modifier: Modifier = Modifier,
    name: String,
    network: String,
    startDate: String,
    status: String,
    imageUrl: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(110.dp)
            .background(Color.DarkGray, shape = RoundedCornerShape(8.dp)),
       horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column (
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
        ){
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(110.dp)
                    .align(Alignment.Start),
                contentAlignment = Alignment.TopStart
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    error = painterResource(id = R.drawable.notfoundimage),
                    contentDescription = "Tv Show Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                        .align(Alignment.Center),
                    onError = { /* Hiçbir şey yapma, boş bırak */ }
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 10.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = name,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = network,
                color = Color.Yellow,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Started on: $startDate",
                color = Color.Gray,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = status,
                color = if (status == "Running") Color.Green else Color.Red,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun HomePageItemPreviewComposable() {
    HomePageItemComposable(
        name = "Title",
        network = "Network",
        startDate = "2023-01-01",
        status = "Running",
        imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRLmB06g043adPuLLEEOgt16YI45b-VjJhIFA&s"
    )
}