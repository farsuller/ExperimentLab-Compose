package com.compose.experiment.features.store.data.remote

import com.compose.experiment.features.store.domain.model.Product
import retrofit2.http.GET

interface ProductsApi {

    @GET("/products")
    suspend fun getProducts() : List<Product>
}