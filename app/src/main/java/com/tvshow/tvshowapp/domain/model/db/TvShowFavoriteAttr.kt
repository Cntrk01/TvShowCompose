package com.tvshow.tvshowapp.domain.model.db

import androidx.room.Entity
import com.tvshow.tvshowapp.domain.model.response.Episode

@Entity(tableName = "favorite")
data class TvShowFavoriteAttr(
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
)
