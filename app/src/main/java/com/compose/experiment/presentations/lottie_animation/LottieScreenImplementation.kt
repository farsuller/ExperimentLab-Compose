package com.compose.experiment.presentations.lottie_animation


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.compose.experiment.R

@Composable
fun LottieScreenImplementation() {
    val lottieDelivery1 by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(
            R.raw.delivery1
        )
    )
    val lottieDelivery2 by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(
            R.raw.delivery2
        )
    )

    val progress1 by animateLottieCompositionAsState(
        composition = lottieDelivery1,
        iterations = LottieConstants.IterateForever
    )
    val progress2 by animateLottieCompositionAsState(
        composition = lottieDelivery1,
        iterations = LottieConstants.IterateForever
    )

    Column {

        LottieAnimation(
            modifier = Modifier.size(300.dp),
            composition = lottieDelivery1,
            progress = { progress1 })
        LottieAnimation(
            modifier = Modifier.size(300.dp),
            composition = lottieDelivery2,
            progress = { progress2 })

        Button(onClick = { }) {
            Text(text = "Play Again")

        }
    }
}