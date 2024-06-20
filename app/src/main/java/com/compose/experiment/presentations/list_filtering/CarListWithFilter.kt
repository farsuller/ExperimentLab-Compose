package com.compose.experiment.presentations.list_filtering

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp


@Composable
fun CarListWithFilter(cars: List<Car>) {
    var filterText by remember { mutableStateOf("") }
    val filteredCars = if (filterText.isEmpty()) {
        cars
    } else {
        cars.filter { it.name.contains(filterText, ignoreCase = true) }
    }

    Column {
        // TextField for filtering cars
        OutlinedTextField(
            value = filterText,
            onValueChange = { filterText = it },
            label = { Text("Filter Cars") },
            singleLine = true,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { /* Handle keyboard done action if needed */ })
        )

        // List of cars
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredCars) { car ->
                CarListItem(car = car)
                HorizontalDivider() // Optional: Add dividers between list items
            }
        }
    }
}

@Composable
fun CarListItem(car: Car) {
    // Composable for rendering individual car item
    Column {
        Text(car.name)
        Text(car.brand)
    }
}

data class Car(val name: String, val brand: String)