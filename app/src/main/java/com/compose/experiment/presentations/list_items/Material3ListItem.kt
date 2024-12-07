package com.compose.experiment.presentations.list_items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun Material3ListItem() {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        var fruits by remember { mutableStateOf(SAMPLE_FRUIT) }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding
        ) {
            items(
                items = fruits,
                key = { fruit -> fruit.name }) { fruit ->
                ListItem(
                    headlineContent = {
                        Text(text = fruit.name)
                    },
                    supportingContent = {
                        Text(text = fruit.description)
                    },
                    overlineContent = {
                        Text(text = fruit.category.name)
                    },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = null,
                            tint = fruit.color
                        )
                    },
                    trailingContent = {
                        Checkbox(
                            checked = fruit.isSelected,
                            onCheckedChange = {
                                fruits = fruits.map { currentFruit ->
                                    if (currentFruit == fruit) {
                                        currentFruit.copy(isSelected = !currentFruit.isSelected)
                                    } else {
                                        currentFruit
                                    }
                                }
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            fruits = fruits.map { currentFruit ->
                                if (currentFruit == fruit) {
                                    currentFruit.copy(isSelected = !currentFruit.isSelected)
                                } else {
                                    currentFruit
                                }
                            }
                        }
                )
                HorizontalDivider()

            }
        }
    }
}