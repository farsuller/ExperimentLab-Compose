package com.compose.experiment.presentations.components
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.min
import kotlin.math.sqrt

class HoneycombShape : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = drawHoneycombPath(size)
        )
    }
}

fun drawHoneycombPath(size: Size): Path {
    return Path().apply {
        val radius = min(size.width / 2f, size.height / 2f)
        honeycombPattern(radius, size)
    }
}

fun Path.honeycombPattern(radius: Float, size: Size) {
    val triangleHeight = (sqrt(3.0) * radius / 2).toFloat()
    val hexagonWidth = triangleHeight * 2

    val horizontalGap = triangleHeight * 1.5f
    val verticalGap = radius * 1.5f

    val hexagonCountHorizontal = (size.width / (hexagonWidth + horizontalGap)).toInt()
    val hexagonCountVertical = (size.height / (radius * 3)).toInt()

    for (row in 0 until hexagonCountVertical) {
        for (col in 0 until hexagonCountHorizontal) {
            val x = col * (hexagonWidth + horizontalGap)
            val y = row * (radius * 3)

            if (col % 2 != 0) {
                moveTo(x + triangleHeight, y + radius)
            } else {
                moveTo(x, y + radius)
            }

            lineTo(x, y + radius * 2)
            lineTo(x + triangleHeight, y + radius * 3)
            lineTo(x + triangleHeight * 2, y + radius * 3)
            lineTo(x + hexagonWidth, y + radius * 2)
            lineTo(x + triangleHeight * 2, y + radius)
            close()
        }
    }
}