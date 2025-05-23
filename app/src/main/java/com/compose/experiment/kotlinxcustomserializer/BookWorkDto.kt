package com.compose.experiment.kotlinxcustomserializer

import kotlinx.serialization.Serializable

@Serializable(with = BookWorkDtoSerializer::class)
data class BookWorkDto(
    val description: String?,
)
