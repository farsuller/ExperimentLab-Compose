package com.compose.experiment.presentations.design_pattern.presentation

import androidx.lifecycle.ViewModel
import com.compose.experiment.presentations.design_pattern.domain.model.Car
import com.compose.experiment.presentations.design_pattern.domain.repository.CarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CarViewModel @Inject constructor(private val carRepository: CarRepository) : ViewModel() {

    private val _cars = MutableStateFlow<List<Car>>(emptyList())
    val cars: StateFlow<List<Car>> get() = _cars

    init {
        // Initialize with some cars for testing
        _cars.value = listOf(
            Car(1, "Toyota", "Camry", 2021),
            Car(2, "Ford", "Explorer", 2022)
        )
    }

    fun addCar(car: Car) {
        _cars.value += car
    }

    fun removeCar(id: Int) {
        _cars.value = _cars.value.filter { it.id != id }
    }
}