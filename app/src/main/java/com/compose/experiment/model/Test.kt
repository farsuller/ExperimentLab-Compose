package com.compose.experiment.model

import kotlinx.serialization.Serializable

@Serializable
data class Test(
    val id: Int,
    val test_name: String? = null,
    val test_last: String? = null,
    val email: String? = null
)