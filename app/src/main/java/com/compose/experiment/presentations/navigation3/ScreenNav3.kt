package com.compose.experiment.presentations.navigation3

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class ScreenNav3 : NavKey {
    @Serializable
    data object Auth: ScreenNav3()

    @Serializable
    data object NestedGraph: ScreenNav3()

    @Serializable
    data object Settings : ScreenNav3()
}