package com.compose.experiment.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val first_name: String? = null,
    val last_name: String? = null,
    val email: String? = null
)