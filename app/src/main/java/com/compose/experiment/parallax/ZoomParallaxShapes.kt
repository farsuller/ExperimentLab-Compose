package com.compose.experiment.parallax

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp

@Composable
fun ZoomParallaxShapes() {
    val scrollState = rememberScrollState()

    // Total depth = number of objects
    val zPosition by remember {
        derivedStateOf {
            0.5f + scrollState.value / 400f
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)

    ) {
        Spacer(Modifier.height(1560.dp))
    }

    /* ---------------- BACKGROUND PARALLAX ---------------- */
    Box(
        Modifier
            .fillMaxSize()
            .graphicsLayer {
                val p = zPosition * 0.15f
                scaleX = 1f + p
                scaleY = 1f + p
                translationY = p * 40f
            },
        contentAlignment = Alignment.Center
    ) {
        Box(Modifier.offset(10.dp, 240.dp).background(Color.Blue).size(60.dp).clip(CircleShape))
        Box(Modifier.offset(129.dp, (-130).dp).background(Color.Blue).size(40.dp))
        Box(Modifier.offset(154.dp, 60.dp).background(Color.Blue).size(60.dp, 40.dp))

        Box(Modifier.offset((-10).dp, 240.dp).background(Color.Blue).size(60.dp).clip(CircleShape))
        Box(Modifier.offset((-129).dp, 130.dp).background(Color.Blue).size(40.dp))
        Box(Modifier.offset((-154).dp, 60.dp).background(Color.Blue).size(60.dp, 40.dp))
    }

    /* ---------------- MIDDLE PARALLAX ---------------- */
    Box(
        Modifier
            .fillMaxSize()
            .graphicsLayer {
                val p = zPosition * 0.3f
                scaleX = 1f + p
                scaleY = 1f + p
                translationY = p * 20f
            },
        contentAlignment = Alignment.Center
    ) {
        Box(Modifier.offset((-50).dp, 120.dp).background(Color.Red).size(30.dp).clip(CircleShape))
        Box(Modifier.offset((-40).dp, (-200).dp).background(Color.Red).size(40.dp).clip(CircleShape))
        Box(Modifier.offset((-170).dp, 260.dp).background(Color.Red).size(20.dp).clip(CircleShape))
    }

    /* ---------------- FOREGROUND (DEPTH STACK) ---------------- */
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CenteredDepthStack(zPosition)
    }

}


@Composable
fun CenteredDepthStack(z: Float) {
    Box(
        Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        DepthItem(index = 0, z, color = Color(0xFFFFC107)) {
            Text("Personal Info")
        }

        DepthItem(index = 1, z, color = Color(0xFF4CAF50)) {
            Text("Skills")
        }

        DepthItem(index = 2, z, color = Color(0xFF3F51B5)) {
            Text("Experience")
        }

        DepthItem(index = 3, z, color = Color(0xFFE91E63)) {
            Text("Portfolio")
        }

        DepthItem(index = 4, z, color = Color(0xFF00BCD4)) {
            Text("Contact")
        }
    }
}

@Composable
fun DepthItem(
    index: Int,
    z: Float,
    color: Color,
    baseSize: Dp = 300.dp,
    content: @Composable () -> Unit
) {
    val local = (z - index).coerceIn(0f, 1f)

    val maxScale = rememberScreenScale(baseSize)

    // fade near the end
    val alpha = when {
        local < 0.2f -> local / 0.2f
        local > 0.85f -> (1f - local) / 0.15f
        else -> 1f
    }.coerceIn(0f, 1f)

    // scale until it fills the screen
    val scale = lerp(
        start = 0.9f,
        stop = maxScale * 1.1f, // slightly overshoot for realism
        fraction = local
    )

    Box(
        Modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.alpha = alpha
            }
            .size(baseSize)
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}


@Composable
fun rememberScreenScale(baseSizeDp: Dp): Float {
    val density = LocalDensity.current
    val config = LocalConfiguration.current

    val screenMinDp = minOf(config.screenWidthDp, config.screenHeightDp).dp

    return with(density) {
        screenMinDp.toPx() / baseSizeDp.toPx()
    }
}

