package com.compose.experiment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.compose.experiment.presentations.shared_element_transition.SharedElementDetailScreen
import com.compose.experiment.presentations.shared_element_transition.SharedElementListScreen
import com.compose.experiment.ui.theme.ExperimentLabTheme
import dagger.hilt.android.AndroidEntryPoint


@OptIn(ExperimentalSharedTransitionApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ExperimentLabTheme(dynamicColor = false) {

                SharedTransitionLayout {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "list") {
                        composable("list") {
                            SharedElementListScreen(
                                onItemClick = { resId, text ->
                                    navController.navigate("detail/$resId/$text")
                                },
                                animatedVisibilityScope = this
                            )
                        }

                        composable("detail/{resId}/{text}",
                            arguments = listOf(
                                navArgument("resId") { type = NavType.IntType },
                                navArgument("text") { type = NavType.StringType }
                            )
                        ) {
                            val resId = it.arguments?.getInt("resId") ?: 0
                            val text = it.arguments?.getString("text") ?: ""
                            SharedElementDetailScreen(
                                resId = resId,
                                text = text,
                                animatedVisibilityScope = this
                            )
                        }
                    }
                }

            }
        }


    }


}


@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.clickableWithoutRipple(
    interactionSource: MutableInteractionSource,
    onClick: () -> Unit
) = composed(
    factory = {
        this.then(
            Modifier.clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { onClick() }
            )
        )
    }
)
