package com.tvshow.tvshowapp.domain.model.detail

import com.google.gson.annotations.SerializedName

data class TvShow(
    val countdown: Any?,
    val country: String?,
    val description: String?,
    @SerializedName("description_source")
    val descriptionSource: String?,
    @SerializedName("end_date")
    val endDate: Any?,
    val episodes: List<Episode>?,
    val genres: List<String>?,
    val id: Int?,
    @SerializedName("image_path")
    val imagePath: String?,
    @SerializedName("image_thumbnail_path")
    val imageThumbnailPath: String?,
    val name: String?,
    val network: String?,
    val permalink: String?,
    val pictures: List<String>?,
    val rating: String?,
    @SerializedName("rating_count")
    val ratingCount: String?,
    val runtime: Int?,
    @SerializedName("start_date")
    val startDate: String?,
    val status: String?,
    val url: String?,
    @SerializedName("youtube_link")
    val youtubeLink: String?
)