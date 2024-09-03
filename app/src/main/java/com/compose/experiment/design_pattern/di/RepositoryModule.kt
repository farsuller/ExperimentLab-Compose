package com.compose.experiment.design_pattern.di

import com.compose.experiment.design_pattern.data.repository.CarRepositoryImpl
import com.compose.experiment.design_pattern.domain.repository.CarRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCarRepository(): CarRepository = CarRepositoryImpl()
}