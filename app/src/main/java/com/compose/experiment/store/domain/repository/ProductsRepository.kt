package com.compose.experiment.store.domain.repository

import arrow.core.Either
import com.compose.experiment.store.domain.model.NetworkError
import com.compose.experiment.store.domain.model.Product

interface ProductsRepository {

    suspend fun getProducts() : Either<NetworkError, List<Product>>

}