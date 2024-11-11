package com.compose.experiment.features.design_pattern.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.compose.experiment.features.design_pattern.domain.model.Car

@Composable
fun CarScreen() {
    val carViewModel: CarViewModel = hiltViewModel()
    val carList by carViewModel.cars.collectAsState(emptyList())

    LaunchedEffect(carList) {
        println("Current car list: $carList")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Car List",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Button(onClick = {
                carViewModel.addCar(Car(carList.size + 1, "Toyota", "Camry", 2021))
            }) {
                Text("Add Car")
            }

            Button(onClick = {
                if (carList.isNotEmpty()) {
                    carViewModel.removeCar(carList.first().id)
                }
            }) {
                Text("Remove Car")
            }

        }

        LazyColumn {
            items(carList) { car ->
                println("Current car: $car")
                CarItem(car)

            }
        }
    }
}

@Composable
fun CarItem(car: Car) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Column {
                Text(text = "Id: ${car.id}", style = MaterialTheme.typography.labelSmall)
                Text(text = "Make: ${car.make}", style = MaterialTheme.typography.labelSmall)
                Text(text = "Model: ${car.model}", style = MaterialTheme.typography.labelSmall)
                Text(text = "Year: ${car.year}", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}