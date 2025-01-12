package com.tvshow.tvshowapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tvshow.tvshowapp.domain.model.db.TvShowFavoriteAttr

@Database(entities = [TvShowFavoriteAttr::class], version = 1, exportSchema = false)
abstract class TvShowDatabase : RoomDatabase(){
    abstract val favoriteDao: TvShowDao
}