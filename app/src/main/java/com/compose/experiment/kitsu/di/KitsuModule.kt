package com.compose.experiment.kitsu.di

import com.compose.experiment.kitsu.data.remote.KitsuApi
import com.compose.experiment.kitsu.data.repository.KitsuRepositoryImpl
import com.compose.experiment.kitsu.domain.repository.KitsuRepository
import com.skydoves.sandwich.retrofit.adapters.ApiResponseCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KitsuModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()


    @Provides
    @Singleton
    fun provideKitsuApi(moshi: Moshi): KitsuApi =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .baseUrl("https://kitsu.io/api/edge/")
            .build()
            .create(KitsuApi::class.java)

    @Provides
    @Singleton
    fun provideKitsuRepository(api: KitsuApi): KitsuRepository = KitsuRepositoryImpl(api)

}