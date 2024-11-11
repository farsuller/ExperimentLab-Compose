package com.compose.experiment.features.store.presenstation

import com.compose.experiment.features.store.domain.model.Product

data class ProductUiState (
    val isLoading : Boolean = false,
    val products : List<Product> = emptyList(),
    val errorMessages : String? = null

)