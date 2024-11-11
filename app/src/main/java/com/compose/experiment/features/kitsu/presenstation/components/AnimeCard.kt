package com.compose.experiment.features.kitsu.presenstation.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.compose.experiment.features.kitsu.domain.model.AnimeData

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.AnimeCard(
    animeData: AnimeData,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    Card(
        onClick = onClick,
        modifier = modifier

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            AsyncImage(
                model = animeData.attributes.posterImage?.original,
                contentDescription = animeData.attributes.canonicalTitle,
                modifier = Modifier
                    .sharedElement(
                        rememberSharedContentState(key = animeData.id),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { _, _ ->
                            // Use tween to specify the animation behavior
                            tween(durationMillis = 500)
                        }
                    )
                    .size(90.dp)
                    .clip(RoundedCornerShape(10.dp))
                    ,
                contentScale = ContentScale.Crop
            )

            Column {
                Row(
                    modifier = Modifier
                        .background(
                            color = Color(0xFFC4C7EB),
                            shape = RoundedCornerShape(30.dp)
                        )
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Star,
                        contentDescription = null,
                        tint = Color.Yellow
                    )
                    Text(text = animeData.attributes.averageRating.toString())
                }

                Text(
                    text = animeData.attributes.canonicalTitle ?: "Default Title",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = animeData.attributes.synopsis ?: "",
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            }
        }
    }

}