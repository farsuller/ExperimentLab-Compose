package com.compose.experiment.store.data.remote

import com.compose.experiment.store.domain.model.Product
import retrofit2.http.GET

interface ProductsApi {

    @GET("/products")
    suspend fun getProducts() : List<Product>
}