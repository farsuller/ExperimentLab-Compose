package com.compose.experiment.presentations.circular_floating_menu

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun CircularFloatingActionMenu() {

    val context = LocalContext.current.applicationContext
    var iconState by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {

        AnimatedVisibility(
            visible = iconState, modifier = Modifier.align(BottomEnd).clip(
                RoundedCornerShape(
                    topStartPercent = 100,
                )
            )
        ) {
            Box(
                modifier = Modifier
                    .align(BottomEnd)
                    .clip(
                        RoundedCornerShape(
                            topStartPercent = 100,
                        )
                    )
                    .background(Color.Blue.copy(alpha = 0.7f))
            ) {
                Box(
                    modifier = Modifier
                        .width(220.dp)
                        .padding(20.dp)
                ) {

                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp)
                    ) {
                        val (dashboard, search, wishlist, logout) = createRefs()
                        AppContent(
                            icon = Icons.Default.MailOutline,
                            text = "Dashboard",
                            modifier = Modifier.constrainAs(dashboard) {
                                top.linkTo(parent.top)
                                end.linkTo(parent.end)
                            },
                            onClick = {
                                Toast.makeText(context,"Selected is $it",Toast.LENGTH_SHORT).show()
                            }
                        )
                        AppContent(
                            icon = Icons.Default.Search,
                            text = "Search",
                            modifier = Modifier
                                .constrainAs(search) {
                                    top.linkTo(parent.top)
                                    start.linkTo(parent.start)
                                }
                                .padding(top = 30.dp, start = 40.dp),
                            onClick = {
                                Toast.makeText(context,"Selected is $it",Toast.LENGTH_SHORT).show()
                            }
                        )
                        AppContent(
                            icon = Icons.Default.ShoppingCart, text = "Wishlist",
                            modifier = Modifier
                                .constrainAs(wishlist) {
                                    top.linkTo(search.bottom)
                                    start.linkTo(parent.start)
                                }
                                .padding(top = 15.dp),
                            onClick = {
                                Toast.makeText(context,"Selected is $it",Toast.LENGTH_SHORT).show()
                            }
                        )
                        AppContent(
                            icon = Icons.AutoMirrored.Filled.ExitToApp, text = "Logout",
                            modifier = Modifier
                                .constrainAs(logout) {
                                    top.linkTo(wishlist.bottom)
                                    start.linkTo(parent.start)
                                }
                                .padding(top = 20.dp, start = 10.dp),
                            onClick = {
                                Toast.makeText(context,"Selected is $it",Toast.LENGTH_SHORT).show()
                            }
                        )
                    }

                }

            }
        }

        Box(
            modifier = Modifier
                .align(BottomEnd)
                .padding(20.dp)
        ) {
            FloatingActionButton(onClick = {
                iconState = !iconState
            }, containerColor = Color.White) {
                Icon(
                    imageVector =
                    if (!iconState) Icons.Default.Add else Icons.Default.Close,
                    contentDescription = "",
                    modifier = Modifier.graphicsLayer(
                        rotationZ = animateFloatAsState(targetValue = if (iconState) 180f else 0f,
                            label = "animate"
                        ).value
                    ),
                    tint = Color.Blue
                )
            }
        }
    }

}

@Composable
fun AppContent(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String,
    onClick : (String)-> Unit
) {
    Box(modifier = modifier) {
        Column(modifier = Modifier.clickable { onClick(text) }) {
            Icon(
                imageVector = icon,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.align(CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = text, style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
            )
        }
    }
}