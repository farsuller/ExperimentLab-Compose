package com.compose.experiment.presentations.animations.shared_element_transition

import kotlinx.serialization.Serializable

@Serializable
data object ListScreen

@Serializable
data class DetailScreen(val resId: Int, val text: String)