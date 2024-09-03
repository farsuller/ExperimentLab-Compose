package com.compose.experiment.design_pattern.domain.repository

import com.compose.experiment.design_pattern.domain.model.Car
import kotlinx.coroutines.flow.Flow

interface CarRepository {
    fun addCar(car: Car)
    fun getCarById(id: Int): Car?
    fun getAllCars(): Flow<List<Car>>
    fun removeCar(id: Int)
}