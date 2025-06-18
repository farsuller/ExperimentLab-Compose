package com.compose.experiment.presentations.navigation3

import androidx.compose.runtime.saveable.Saver
import androidx.navigation3.runtime.NavKey
import com.compose.experiment.R
import com.compose.experiment.presentations.navigation3.BottomNav3Bar.Home
import com.compose.experiment.presentations.navigation3.BottomNav3Bar.Profile
import com.compose.experiment.presentations.navigation3.BottomNav3Bar.Search
import kotlinx.serialization.Serializable

val bottomBarItems = listOf<BottomNav3Bar>(
    BottomNav3Bar.Home,
    BottomNav3Bar.Search,
    BottomNav3Bar.Profile
)

@Serializable
sealed class BottomNav3Bar (
    val icon : Int,
    val title : String,
): NavKey {

    @Serializable
    data object Home : BottomNav3Bar(icon = R.drawable.ic_nav_home, title = "Home")

    @Serializable
    data object Search : BottomNav3Bar(icon = R.drawable.ic_nav_search, title = "Search")

    @Serializable
    data object Profile : BottomNav3Bar(icon = R.drawable.ic_nav_person, title = "Profile")
}

val BottomNav3BarSaver = Saver<BottomNav3Bar, String>(
    save = { it::class.simpleName ?: "Unknown" },
    restore = {
        when(it){
            Home::class.simpleName -> Home
            Search::class.simpleName -> Search
            Profile::class.simpleName -> Profile
            else -> Home
        }
    }
)