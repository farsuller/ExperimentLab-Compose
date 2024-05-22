package com.compose.experiment

object MathUtils {
    fun multiply(price: Double, quantity: Int): Double {
        return price * quantity
    }

    fun divide(price: Double, quantity: Int): Double {
        if (quantity == 0) {
            throw IllegalArgumentException("Quantity cannot be zero for division.")
        }
        return price / quantity
    }
}