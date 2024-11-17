package com.tvshow.tvshowapp.uielements

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

object UiElementsExt {

    //@Composable
    //fun getColorFromResource(colorResId: Int): Color {
    //    return Color(ContextCompat.getColor(LocalContext.current, colorResId))
    //}
    val getColorFromResource: @Composable (Int) -> Color = {
        Color(ContextCompat.getColor(LocalContext.current, it))
    }
}