package com.tvshow.tvshowapp.domain.model.attr

import com.tvshow.tvshowapp.domain.model.response.Episode

data class TvShowDetailAttr(
    val name : String?,
    val status : String?,
    val startDate : String?,
    val endDate : Any?,
    val rating : String?,
    val network : String?,
    val description : String?,
    val imageThumbnailPath: String?,
    val imageList : List<String>?,
    val url : String?,
    val descriptionSource : String?,
    val youtubeLink : String?,
    val episodes : List<Episode>?
)