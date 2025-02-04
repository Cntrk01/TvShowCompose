package com.tvshow.tvshowapp.domain.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tvshow.tvshowapp.domain.model.response.Episode

@Entity(tableName = "favorite")
data class TvShowFavoriteAttr(
    @PrimaryKey
    val showId : String,
    val showPictures : List<String>,
    val showName : String,
    val showStatus : String,
    val showStartDate : String,
    val showEndDate : String,
    val showRating : String,
    val showNetwork : String,
    val showCountry : String,
    val showUrl : String?,
    val showDescriptionSource : String?,
    val showYoutubeLink : String?,
    val showEpisodes : List<Episode>?,
    val showImageThumbnail : String?,
    val showDescription: String?,
)
