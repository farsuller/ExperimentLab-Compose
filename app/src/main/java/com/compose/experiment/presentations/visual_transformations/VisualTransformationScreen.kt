package com.compose.experiment.presentations.visual_transformations

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.compose.experiment.CreditCardClass
import com.compose.experiment.CreditCardObject
import com.compose.experiment.DateVisualTransformation
import com.compose.experiment.DateVisualTransformationFormatted
import com.compose.experiment.NumberVisualTransformation
import com.compose.experiment.ThousandSeparatorTransformation
import com.compose.experiment.creditCard
import com.compose.experiment.creditCardLambda
import com.compose.experiment.priceFilter

@Composable
    fun VisualTransformationScreen() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            var creditCardLambdaText by remember {
                mutableStateOf("")
            }

            var creditCardText by remember {
                mutableStateOf("")
            }

            var creditCardClassText by remember {
                mutableStateOf("")
            }

            var creditCardObjectText by remember {
                mutableStateOf("")
            }

            var mobileNumberFilterText by remember {
                mutableStateOf("")
            }

            var dateVisualText by remember {
                mutableStateOf("")
            }

            var dateVisualFormattedText by remember {
                mutableStateOf("")
            }

            var thousandSeparatorText by remember {
                mutableStateOf("")
            }
            var priceFilterText by remember {
                mutableStateOf("")
            }
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column {
                    TextField(
                        value = creditCardObjectText,
                        onValueChange = {
                            creditCardObjectText = it
                        },
                        visualTransformation = CreditCardObject,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    TextField(
                        value = creditCardClassText,
                        onValueChange = {
                            creditCardClassText = it
                        },
                        visualTransformation = CreditCardClass(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        value = creditCardLambdaText,
                        onValueChange = {
                            creditCardLambdaText = it
                        },
                        visualTransformation = creditCardLambda,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        value = creditCardText,
                        onValueChange = {
                            creditCardText = it
                        },
                        visualTransformation = {
                            creditCard(it)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )

                    Spacer(modifier = Modifier.height(10.dp))


                    TextField(
                        value = mobileNumberFilterText,
                        onValueChange = { newText ->
                            mobileNumberFilterText = newText.filter { it.isDigit() }
                                .take(11) // Ensure only digits and max length of 11
                        },
                        visualTransformation = NumberVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

                    )


                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        value = dateVisualText,
                        onValueChange = {
                            dateVisualText = it
                        },
                        label = { Text("YYYY-MM-DD") },
                        visualTransformation = DateVisualTransformation,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        value = dateVisualFormattedText,
                        onValueChange = {
                            dateVisualFormattedText = it
                        },
                        label = { Text("DD-MM-YYYY") },
                        visualTransformation = DateVisualTransformationFormatted(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        value = thousandSeparatorText,
                        onValueChange = {
                            thousandSeparatorText = it
                        },
                        visualTransformation = ThousandSeparatorTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        value = priceFilterText,
                        onValueChange = {
                            priceFilterText = it
                        },
                        visualTransformation = { priceFilter(priceFilterText) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                }

            }
        }
    }