package com.compose.experiment.encryption

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.datastore.dataStore
import androidx.wear.compose.material.Text
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

val Context.dataStore by dataStore(
    fileName = "user-preferences",
    serializer = UserPreferencesSerializer
)

val SECRET_TOKEN = "Hello, you decrypted me great!"

@Composable
fun EncryptDecryptScreen(
    context: Context
) {

    val ctx = context.applicationContext

    val dataStore = ctx.dataStore

    val scope = rememberCoroutineScope()
    var text by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                scope.launch {
                    dataStore.updateData {
                        UserPreferences(token = SECRET_TOKEN)
                    }
                }
            }) {
                Text(text = "Encrypt")
            }

            Button(onClick = {
                scope.launch {
                    text = dataStore.data.first().token ?: ""
                }
            }) {
                Text(text = "Decrypt")
            }


            Text(
                color = Color.Black,
                text = text
            )
        }
    }
}