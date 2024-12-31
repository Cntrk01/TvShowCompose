package com.tvshow.tvshowapp.common.uielements

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun TvShowErrorComposable(
    errorMessage: String,
    isRetryAvailable: Boolean = false,
    retryAction: () -> Unit = {},
): Unit {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .padding(16.dp),
                text = errorMessage,
                color = Color.Red,
                textAlign = TextAlign.Center
            )

            if (isRetryAvailable) {
                Button(onClick = {
                     retryAction()
                }) {
                    Text(text = "Retry")
                }
            }
        }
    }
}