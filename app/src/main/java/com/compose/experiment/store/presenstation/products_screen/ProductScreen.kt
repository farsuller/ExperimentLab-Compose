package com.compose.experiment.store.presenstation.products_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.compose.experiment.store.presenstation.ProductUiState
import com.compose.experiment.store.presenstation.ProductViewModel
import com.compose.experiment.store.presenstation.components.LoadingDialog
import com.compose.experiment.store.presenstation.components.ProductCard
import com.compose.experiment.store.presenstation.components.ProductTopBar
import com.compose.experiment.utils.Event
import com.compose.experiment.utils.EventBus

@Composable
fun ProductScreen() {
    val viewModel = hiltViewModel<ProductViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val context = LocalContext.current

    LaunchedEffect(key1 = lifecycle) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
            EventBus.events.collect{event->
                if(event is Event.Toast){
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    ProductContent(state = state)
}

@Composable
fun ProductContent(
    state: ProductUiState
) {

    LoadingDialog(isLoading = state.isLoading)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            ProductTopBar(title = "Products")
        }
    ) { innerPadding ->
        LazyVerticalStaggeredGrid(
            modifier = Modifier.padding(
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding()
            ),
            columns = StaggeredGridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(state.products){ product ->
                ProductCard(product = product)
            }
        }
    }
}