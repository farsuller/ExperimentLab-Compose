package com.compose.experiment.presentations.navigate_back_withResult


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigateBackWithResult() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "screen1"
    ) {
        composable("screen1") { entry ->
            val text = entry.savedStateHandle.get<String>("my_text")
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                text?.let {
                    Text(text = text)
                }
                Button(onClick = {
                    navController.navigate("screen2") {
                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                }) {
                    Text(text = "Go to screen2")
                }
            }
        }
        composable("screen2") {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                var text by remember {
                    mutableStateOf("")
                }
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier.width(300.dp)
                )
                Button(onClick = {
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("my_text", text)
                    navController.popBackStack()
                }) {
                    Text(text = "Apply")
                }
            }
        }
    }
}