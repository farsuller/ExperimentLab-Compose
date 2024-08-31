package com.compose.experiment.presentations.design_pattern.domain.repository

import com.compose.experiment.presentations.design_pattern.domain.model.Car

class CarRepositoryImpl : CarRepository {

    private val carList = mutableListOf<Car>()

    override fun addCar(car: Car) {
        carList.add(car)
    }

    override fun getCarById(id: Int): Car? {
        return carList.find { it.id == id }
    }

    override fun getAllCars(): List<Car> {
        return carList
    }

    override fun removeCar(id: Int) {
        carList.removeIf { it.id == id }
    }
}