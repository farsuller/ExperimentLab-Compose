package com.compose.experiment.kotlinxcustomserializer

import kotlinx.serialization.Serializable

@Serializable
data class DescriptionDto(
    val value : String
)
