package com.tvshow.tvshowapp.data.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tvshow.tvshowapp.data.db.TvShowDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        application: Application
    ): TvShowDatabase{
        return Room.databaseBuilder(
            context = application,
            name = "TvShowFavorite.db",
            klass = TvShowDatabase::class.java,
        ).allowMainThreadQueries() //main threadde sorgu çalıştırılmasının önüne geçer.ANR hatasına sebeb verebilir bunun sebebide uzun süren sorgular olabilir.Bloke edebilir
         .fallbackToDestructiveMigration() //eski veritabanı şemalarını en son versiona taşırken geçiş kümesi bulunmasa IllegalStateException atılır.Fakat bu methodla tablo yıkılıp tekrar oluşur böylelikle hata almamak için kullanılır.
         .build()
    }

}