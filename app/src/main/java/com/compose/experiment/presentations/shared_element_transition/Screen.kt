package com.compose.experiment.presentations.shared_element_transition

import kotlinx.serialization.Serializable

@Serializable
data object ListScreen

@Serializable
data class DetailScreen(
    val companyName: String,
    val companyImage: String,
    val jobPosition: String)