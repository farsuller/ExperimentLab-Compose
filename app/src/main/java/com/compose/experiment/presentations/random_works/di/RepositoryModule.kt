package com.compose.experiment.presentations.random_works.di

import com.compose.experiment.presentations.random_works.data.repository.StudentRepositoryImpl
import com.compose.experiment.presentations.random_works.domain.repository.StudentRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindStudentRepository(studentRepositoryImpl: StudentRepositoryImpl): StudentRepository


}