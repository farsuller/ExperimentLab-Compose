package com.compose.experiment.store.di

import com.compose.experiment.store.data.remote.ProductsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


// AppsModule provides instances of dependencies used in the application.
@Module
@InstallIn(SingletonComponent::class)
object AppsModule {

    // Provides an instance of ProductsApi.
    // This method constructs a Retrofit instance configured with a base URL and Gson converter,
    // and then creates an implementation of the ProductsApi interface.
    @Provides
    @Singleton
    fun providesProductsApi() : ProductsApi{
        return Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductsApi::class.java)
    }
}