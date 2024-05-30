package com.compose.experiment.presentations.blurred_carousel_pager

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.roundToInt


@Composable
fun BlurredCarouselPager() {
    val pagerState = rememberPagerState(pageCount = { locations.count() })
    Column {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(.7f)
                .padding(top = 32.dp),
            pageSpacing = 1.dp,
            beyondViewportPageCount = locations.count()
        ) { page ->
            Box(modifier = Modifier
                .zIndex(page * 2f)
                .padding(
                    start = 64.dp,
                    end = 32.dp
                )
                .graphicsLayer {
                    val startOffset = pagerState.startOffsetForPage(page)
                    translationX = size.width * (startOffset * .99f)

                    alpha = (2f - startOffset) / 2

                    val blur = (startOffset * 20).coerceAtLeast(.1f)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        renderEffect = RenderEffect
                            .createBlurEffect(
                                blur, blur, Shader.TileMode.DECAL
                            )
                            .asComposeRenderEffect()
                    }

                    val scale = 1f - (startOffset * .1f)
                    scaleX = scale
                    scaleY = scale
                }
                .clip(RoundedCornerShape(20.dp))) {
                Image(
                    painter = painterResource(id = locations[page].image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .weight(.3f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val verticalState = rememberPagerState(pageCount = {
                locations.count()
            })
            VerticalPager(
                state = verticalState, modifier = Modifier
                    .weight(1f)
                    .height(86.dp),
                userScrollEnabled = false, horizontalAlignment = Alignment.Start
            ) { page ->
                Column(verticalArrangement = Arrangement.Center) {
                    Text(
                        text = locations[page].title,
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Thin,
                            fontSize = 28.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = locations[page].subtitle,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    )
                }
            }
            LaunchedEffect(Unit) {
                snapshotFlow {
                    Pair(
                        pagerState.currentPage,
                        pagerState.currentPageOffsetFraction
                    )
                }.collect { (page, offset) ->
                    verticalState.scrollToPage(page, offset)
                }
            }

            val interpolatedRating by remember {
                derivedStateOf {
                    val position = pagerState.offsetForPage(0)

                    val from = floor(position).roundToInt()
                    val to = ceil(position).roundToInt()

                    val fromRating = locations[from].rating.toFloat()
                    val toRating = locations[to].rating.toFloat()

                    val fraction = position - position.toInt()

                    fromRating + ((toRating - fromRating) * fraction)
                }
            }
            RatingBar(rating = interpolatedRating)


        }
    }
}

fun PagerState.offsetForPage(page: Int) = (currentPage - page) + currentPageOffsetFraction

fun PagerState.startOffsetForPage(page: Int) = offsetForPage(page).coerceAtLeast(0f)
