package com.compose.experiment.design_pattern.presentation

import androidx.lifecycle.ViewModel
import com.compose.experiment.design_pattern.domain.model.Car
import com.compose.experiment.design_pattern.domain.repository.CarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CarViewModel @Inject constructor
    (private val carRepository: CarRepository) : ViewModel() {

    val cars: Flow<List<Car>> = carRepository.getAllCars()

    fun addCar(car: Car) {
        carRepository.addCar(car)
    }

    fun removeCar(id: Int) {
        carRepository.removeCar(id)
    }
}