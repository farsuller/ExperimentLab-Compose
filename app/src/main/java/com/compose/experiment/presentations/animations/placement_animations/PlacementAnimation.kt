package com.compose.experiment.presentations.animations.placement_animations

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class Student(val point: Int, val name: String)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlacementAnimation() {

    val students = listOf(
        Student(name = "test1", point = 5),
        Student(name = "test2", point = 15),
        Student(name = "test3", point = 10),
        Student(name = "test4", point = 19),
        Student(name = "test5", point = 8),
        Student(name = "test6", point = 16),
    )

    var sortByPoint by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(
                if (sortByPoint) students.sortedBy { it.point } else students,
                key = { it.name }
            ) { s ->
                Box(
                    modifier = Modifier
                        // `animateItemPlacement` animates the position change of each item within the list
                        // whenever the list's order changes, using a `tween` duration of 500ms.
                        .animateItem(tween(500))
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xff212121))
                        // `animateContentSize` animates any size changes within the item content
                        // using a `tween` duration of 500ms.
                        .animateContentSize(tween(500))
                        .padding(4.dp)
                ) {
                    Text(
                        text = s.name,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                // Toggle the sorting state between sorted by points and original order.
                sortByPoint = !sortByPoint
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(text = "Sort List")
        }
    }
}