package com.compose.experiment.presentations.pagination

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.compose.experiment.clickableWithoutRipple

@Composable
fun PaginatedListScreen() {
    val viewModel = viewModel<PaginationViewModel>()
    val state = viewModel.state
    val context = LocalContext.current

        LazyColumn(modifier = Modifier.fillMaxSize()) {

            items(state.items.size) { index ->
                val item = state.items[index]

                if (index >= state.items.size - 1 && !state.endReached && !state.isLoading) {
                    viewModel.loadNextItems()
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickableWithoutRipple {
                            Toast.makeText(context, "Item clicked ${item.title}", Toast.LENGTH_SHORT).show()
                        }
                ) {
                    Text(
                        text = item.title,
                        fontSize = 20.sp,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = item.description)

                }
            }

            item {
                if (state.isLoading) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }