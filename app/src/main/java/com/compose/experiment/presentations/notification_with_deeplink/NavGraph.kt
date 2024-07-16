package com.compose.experiment.presentations.notification_with_deeplink

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink

const val MY_URI = "https://stevdza-san.com"
const val MY_ARG = "message"

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = NotificationMainScreen
    ) {
        composable<NotificationMainScreen>{
            MainScreen(navController = navController)
        }
        composable<NotificationDetailsScreen>( deepLinks = listOf(navDeepLink { uriPattern = "$MY_URI/$MY_ARG={$MY_ARG}" })) {
            val arguments = it.arguments

            arguments?.getString(MY_ARG)?.let { message ->
                DetailsScreen(message = message)
            }

        }
    }
}