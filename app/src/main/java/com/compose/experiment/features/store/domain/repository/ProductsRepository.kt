package com.compose.experiment.features.store.domain.repository

import arrow.core.Either
import com.compose.experiment.features.store.domain.model.NetworkError
import com.compose.experiment.features.store.domain.model.Product

interface ProductsRepository {

    suspend fun getProducts() : Either<NetworkError, List<Product>>

}