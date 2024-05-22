package com.compose.experiment.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Menu(
    val id: Int,
    @SerialName("menus_name") val menusName: String? = null,
    @SerialName("is_available") val isAvailable: Boolean? = null,
    @SerialName("menus_image") val menusImage: String? = null,
)