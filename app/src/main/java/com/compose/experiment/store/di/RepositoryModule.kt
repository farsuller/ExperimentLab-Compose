package com.compose.experiment.store.di

import com.compose.experiment.store.data.repository.ProductRepositoryImpl
import com.compose.experiment.store.domain.repository.ProductsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// RepositoryModule provides the implementation for the ProductsRepository interface.
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    // Binds the ProductRepositoryImpl to the ProductsRepository interface.
    // This means that whenever a ProductsRepository is needed, Dagger Hilt will provide an instance of ProductRepositoryImpl.
    @Binds
    @Singleton
    abstract fun bindProductsRepository(impl: ProductRepositoryImpl): ProductsRepository
}