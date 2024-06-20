package com.compose.experiment.presentations.hexagon_grid

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.compose.experiment.clickableWithoutRipple
import com.compose.experiment.components.HexagonShape
import com.compose.experiment.model.Menus

@Composable
fun HexagonalGrid(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy((-120).dp),


        ) {

        items(Menus.entries.toTypedArray().size) { index ->
            val topPadding = if (index % 2 == 1) 90.dp else 0.dp
            val startOffsetX = if (index % 2 == 1) (-40).dp else 0.dp

            HexagonImg(
                index = index,
                modifier = Modifier
                    .padding(top = topPadding)
                    .offset(x = startOffsetX)
                    .clickableWithoutRipple(
                        onClick = {
                            Toast
                                .makeText(
                                    context,
                                    "Selected ${Menus.entries[index].text}",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }
                    ),
            )
        }
    }
}


@Composable
fun HexagonImg(index: Int, modifier: Modifier = Modifier) {
    val menuList = Menus.entries.toTypedArray()
    val myShape = HexagonShape()
    Box(
        modifier = modifier
            .size(200.dp)
            .rotate(30f),
//        .drawWithContent {
//            drawContent()
//            drawPath(
//                path = drawCustomHexagonPath(size),
//                color = Color.Black,
//                style = Stroke(
//                    width = 4.dp.toPx(),
//                    pathEffect = PathEffect.cornerPathEffect(30f)
//                )
//
//            )
//        }
//        .wrapContentSize()
    ) {
        Column(modifier = Modifier
            .rotate(-30f)
            .fillMaxSize()
            .graphicsLayer {
                shadowElevation = 8.dp.toPx()
                shape = myShape
                clip = true
                rotationZ = 30f
            }
            .background(color = MaterialTheme.colorScheme.primary),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            AsyncImage(
                modifier = Modifier
                    .rotate(-30f)
                    .offset(x = (-5).dp)
                    .height(100.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(menuList[index].menuImage)
                    .crossfade(true).build(),
                contentDescription = "Logo",
                contentScale = ContentScale.Fit
            )

            Text(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .rotate(-30f)
                    .offset(x = 25.dp),
                text = menuList[index].text,
                fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
            )
        }

    }
}