package com.tvshow.tvshowapp.data.mapper

import com.tvshow.tvshowapp.domain.model.TvShow

data class TvShowHomePage(
    val permaLink : String,
    val id : Int,
    val imageThumbnailPath : String,
    val name : String,
    val startDate : String,
    val country : String,
    val network : String,
    val status : String,
)

fun TvShow.toShowMapper(): TvShowHomePage {

    return TvShowHomePage(
        permaLink = permalink ?: "",
        id = id ?: 0,
        name = name ?: "",
        imageThumbnailPath = imageThumbnailPath ?: "",
        startDate = startDate ?: "",
        country = country ?: "",
        network = network ?: "",
        status = status ?: ""
    )
}
