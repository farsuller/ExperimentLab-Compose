package com.compose.experiment.store.presenstation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.experiment.store.domain.repository.ProductsRepository
import com.compose.experiment.store.presenstation.util.sendEvent
import com.compose.experiment.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ProductUiState())
    val state = _state.asStateFlow()

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            productRepository.getProducts()
                .onRight { products ->
                    _state.update {
                        it.copy(products = products)
                    }
                }
                .onLeft { error ->
                    _state.update {
                        it.copy(errorMessages = error.error.message)
                    }
                    sendEvent(Event.Toast(error.error.message))
                }

            _state.update {
                it.copy(isLoading = false)
            }
        }
    }

}