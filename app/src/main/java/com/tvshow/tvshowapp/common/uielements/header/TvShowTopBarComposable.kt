package com.tvshow.tvshowapp.common.uielements.header

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tvshow.myapplication.R

@Composable
fun TopBarComposable(
    modifier: Modifier = Modifier,
    tvShowHeaderType: TvShowHeaderType = TvShowHeaderType.SIMPLE,
    backClick: () -> Unit = {},
    headerTitle: String = "",
) {
    val interactionSource by remember { mutableStateOf(MutableInteractionSource()) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.cardBackground))
            .height(60.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (tvShowHeaderType == TvShowHeaderType.MULTI) {
            Icon(
                modifier = Modifier
                    .clickable(indication = null, interactionSource = interactionSource) {
                        backClick()
                    }
                    .padding(start = 10.dp)
                    .size(25.dp),
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = "Back",
                tint = colorResource(id = R.color.topBarIconColor)
            )
        }

        Text(
            modifier = Modifier.padding(start = 15.dp),
            text = headerTitle,
            fontSize = 18.sp,
            color = colorResource(id = R.color.topBarTextColor),
            fontWeight = FontWeight.Bold
        )
    }
}