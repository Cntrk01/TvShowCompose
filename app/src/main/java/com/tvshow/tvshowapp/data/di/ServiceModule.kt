package com.tvshow.tvshowapp.data.di

import com.tvshow.tvshowapp.data.network.TvShowService
import com.tvshow.tvshowapp.data.repository.TvShowRepositoryImpl
import com.tvshow.tvshowapp.domain.repository.TvShowRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    private const val SERVICE_URL = "https://www.episodate.com/api/"

    @Provides
    @Singleton
    fun provideTvShowService(): TvShowService {
        return Retrofit.Builder()
            .baseUrl(SERVICE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TvShowService::class.java)
    }

    @Provides
    @Singleton
    fun provideTvShowRepository(tvShowService: TvShowService): TvShowRepository {
        return TvShowRepositoryImpl(tvShowService)
    }
}