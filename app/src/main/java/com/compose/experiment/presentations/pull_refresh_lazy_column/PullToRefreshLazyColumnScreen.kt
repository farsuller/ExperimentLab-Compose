package com.compose.experiment.presentations.pull_refresh_lazy_column

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PullToRefreshLazyColumnScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val items = remember {
            (1..100).map { "Item $it" }
        }
        var isRefreshing by remember {
            mutableStateOf(false)
        }
        val scope = rememberCoroutineScope()
        val scrollState = rememberLazyListState()
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
        ) { it ->
            PullToRefreshLazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = it.calculateTopPadding(),
                        bottom = it.calculateBottomPadding()
                    ),
                items = items,
                content = { itemTitle ->
                    Text(
                        text = itemTitle,
                        modifier = Modifier
                            .padding(16.dp)
                    )
                },
                isRefreshing = isRefreshing,
                onRefresh = {
                    scope.launch {
                        isRefreshing = true
                        delay(3000L) // Simulated API call
                        isRefreshing = false
                        scrollState.animateScrollToItem(0)
                    }
                },
                onRefreshClicked = { refresh ->
                    isRefreshing = refresh
                },
                lazyListState = scrollState
            )
        }
    }
}