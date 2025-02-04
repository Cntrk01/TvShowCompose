package com.tvshow.tvshowapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tvshow.tvshowapp.domain.model.attr.TvShowDetailAttr
import com.tvshow.tvshowapp.domain.model.db.TvShowFavoriteAttr
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShow(tvShow: TvShowFavoriteAttr)

    //tekte tüm dataları getirmicem sayfadaki bulunan kadar getiricem.Onun için bi bakacağım
    @Query("SELECT * FROM favorite")
    fun getAllTvShows(): Flow<List<TvShowFavoriteAttr>>

    @Query("DELETE FROM favorite WHERE showId = :showId")
    suspend fun deleteTvShow(showId: String)

    @Query("SELECT COUNT(*) FROM  favorite WHERE showId = :showId")
    suspend fun isSavedTvShow(showId: String): Boolean

    @Query("SELECT * FROM favorite WHERE showId = :showId")
    suspend fun getItemFromDb(showId: String) : TvShowFavoriteAttr?
}