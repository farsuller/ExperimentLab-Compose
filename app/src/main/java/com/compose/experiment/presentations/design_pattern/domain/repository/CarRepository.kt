package com.compose.experiment.presentations.design_pattern.domain.repository

import com.compose.experiment.presentations.design_pattern.domain.model.Car
import kotlinx.coroutines.flow.Flow

sealed interface CarRepository {
    fun addCar(car: Car)
    fun getCarById(id: Int): Car?
    fun getAllCars(): Flow<List<Car>>
    fun removeCar(id: Int)
}