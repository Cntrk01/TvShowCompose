package com.tvshow.tvshowapp.uielements

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.tvshow.myapplication.R

@Composable
fun TvShowLoadingComposable(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(150.dp)
            .width(150.dp)
            .zIndex(1f),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .zIndex(1f),
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        )
        {
            LottieAnimation()
        }
    }
}

@Composable
private fun LottieAnimation() {
    val composite = rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.loading_animation)
    )
    val progressAnim = animateLottieCompositionAsState(
        composition = composite.value, isPlaying = true,
        iterations = LottieConstants.IterateForever,
        speed = 1f
    )

    com.airbnb.lottie.compose.LottieAnimation(
        modifier = Modifier.fillMaxWidth(),
        composition = composite.value,
        progress = progressAnim.value,
        alignment = Alignment.Center
    )
}