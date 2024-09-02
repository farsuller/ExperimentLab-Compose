package com.compose.experiment.presentations.shared_element_transition


import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.compose.experiment.data.Experience

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.SharedElementListScreen(
    onItemClick: (Experience) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope
) {

    val experiences = Experience.entries.toList()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        itemsIndexed(experiences) { index, exp ->
            val text = exp.company
            Row(modifier = Modifier
                .fillMaxWidth()
                .clickable { onItemClick(exp) }) {

                // Define the shared element transition for the image
                AsyncImage(
                    modifier = Modifier
                        .aspectRatio(16 / 9f)
                        .weight(1f)
                        // Use sharedElement to specify the shared element transition
                        .sharedElement(
                            state = rememberSharedContentState(key = "image/${experiences[index].image}"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            // Use boundsTransform to specify the transformation during the transition
                            boundsTransform = { _, _ ->
                                // Use tween to specify the animation behavior
                                tween(durationMillis = 500)
                            }
                        ),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(experiences[index].image)
                        .crossfade(true).build(),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(16.dp))
                // Define the shared element transition for the text
                Text(
                    text = text,
                    modifier = Modifier
                        .weight(1F)
                        // Use sharedElement to specify the shared element transition
                        .sharedElement(
                            state = rememberSharedContentState(key = "text/${experiences[index].company}"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            // Use boundsTransform to specify the transformation during the transition
                            boundsTransform = { _, _ ->
                                // Use tween to specify the animation behavior
                                tween(durationMillis = 500)
                            }
                        )
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.SharedElementDetailScreen(
     companyName: String,
     companyImage: String,
     jobPosition: String,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Define the shared element transition for the image
        AsyncImage(
            modifier = Modifier
                .aspectRatio(16 / 9f)
                .weight(1f)
                // Use sharedElement to specify the shared element transition
                .sharedElement(
                    state = rememberSharedContentState(key = "image/$companyImage"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    // Use boundsTransform to specify the transformation during the transition
                    boundsTransform = { _, _ ->
                        // Use tween to specify the animation behavior
                        tween(durationMillis = 500)
                    }
                ),
            model = ImageRequest.Builder(LocalContext.current)
                .data(companyImage)
                .crossfade(true).build(),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Define the shared element transition for the text
        Text(
            text = companyName,
            modifier = Modifier
                .weight(1F)
                // Use sharedElement to specify the shared element transition
                .sharedElement(
                    state = rememberSharedContentState(key = "text/$companyName"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    // Use boundsTransform to specify the transformation during the transition
                    boundsTransform = { _, _ ->
                        // Use tween to specify the animation behavior
                        tween(durationMillis = 500)
                    }
                )
        )
    }
}