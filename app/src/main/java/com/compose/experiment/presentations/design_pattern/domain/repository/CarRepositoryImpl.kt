package com.compose.experiment.presentations.design_pattern.domain.repository

import com.compose.experiment.presentations.design_pattern.domain.model.Car
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CarRepositoryImpl : CarRepository {

    private val carList = MutableStateFlow(
        listOf(
            Car(1, "Toyota", "Camry", 2021),
            Car(2, "Ford", "Explorer", 2022),
            Car(3, "Honda", "Civic", 2023),
            Car(4, "Chevrolet", "Malibu", 2021),
            Car(5, "BMW", "X5", 2022)
        )
    )

    override fun addCar(car: Car) {
        val updatedList = carList.value.toMutableList().apply { add(car) }
        carList.value = updatedList
    }

    override fun getCarById(id: Int): Car? {
        return carList.value.find { it.id == id }
    }

    override fun getAllCars(): Flow<List<Car>> {
        return carList.asStateFlow() // Expose carList as Flow
    }

    override fun removeCar(id: Int) {
        val updatedList = carList.value.filter { it.id != id }
        carList.value = updatedList
    }
}