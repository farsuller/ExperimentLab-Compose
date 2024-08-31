package com.compose.experiment.presentations.design_pattern.di

import com.compose.experiment.presentations.design_pattern.domain.repository.CarRepository
import com.compose.experiment.presentations.design_pattern.domain.repository.CarRepositoryImpl
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
    fun provideCarRepository(): CarRepository {
        return CarRepositoryImpl()
    }
}