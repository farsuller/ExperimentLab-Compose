package com.compose.experiment.presentations.components

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.math.sqrt

class RotatedHexagonShape : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = drawRotatedHexagonPath(size)
        )
    }
}

fun drawRotatedHexagonPath(size: Size): Path {
    return Path().apply {
        val radius = min(size.width / 2f, size.height / 2f)
        rotatedHexagon( radius, size, -90f)
    }
}

fun Path.rotatedHexagon(radius: Float, size: Size, degrees: Float) {
    val triangleHeight = (sqrt(3.0) * radius / 2).toFloat()
    val centerX = size.width / 2f
    val centerY = size.height / 2f

    // Convert degrees to radians
    val radians = Math.toRadians(degrees.toDouble())
    val cosA = Math.cos(radians).toFloat()
    val sinA = Math.sin(radians).toFloat()

    moveTo(centerX, centerY + radius)
    lineTo(
        centerX + triangleHeight * cosA,
        centerY + triangleHeight * sinA
    )
    lineTo(
        centerX + radius * cosA,
        centerY - radius * sinA
    )
    lineTo(centerX, centerY - radius)
    lineTo(
        centerX - radius * cosA,
        centerY - radius * sinA
    )
    lineTo(
        centerX - triangleHeight * cosA,
        centerY + triangleHeight * sinA
    )

    close()
}