package com.tvshow.tvshowapp.data.di

import com.tvshow.tvshowapp.data.network.TvShowService
import com.tvshow.tvshowapp.data.repository.TvShowServiceRepositoryImpl
import com.tvshow.tvshowapp.domain.repository.TvShowServiceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    private const val SERVICE_URL = "https://www.episodate.com/api/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideTvShowService(): TvShowService {
        return Retrofit.Builder()
            .baseUrl(SERVICE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient())
            .build()
            .create(TvShowService::class.java)
    }

    @Provides
    @Singleton
    fun provideTvShowRepository(tvShowService: TvShowService): TvShowServiceRepository {
        return TvShowServiceRepositoryImpl(tvShowService)
    }
}