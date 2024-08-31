package com.compose.experiment.store.data.repository

import arrow.core.Either
import com.compose.experiment.store.data.mapper.toNetworkError
import com.compose.experiment.store.data.remote.ProductsApi
import com.compose.experiment.store.domain.model.NetworkError
import com.compose.experiment.store.domain.model.Product
import com.compose.experiment.store.domain.repository.ProductsRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productsApi: ProductsApi
) : ProductsRepository {
    override suspend fun getProducts(): Either<NetworkError, List<Product>> {
        return Either.catch {
            productsApi.getProducts()
        }.mapLeft { it.toNetworkError() }
    }
}