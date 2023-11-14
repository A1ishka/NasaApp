package by.bsuir.makogon.alina.navigation.bottom_navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import by.bsuir.makogon.alina.R

sealed class BottomItem(var title: Int, val iconID: ImageVector, val route: String){
    object Home: BottomItem(title = R.string.title_screen_1, iconID = Icons.Default.Home, route = "screen_1")
    object Profile: BottomItem(title = R.string.title_screen_2, iconID = Icons.Default.Person, route = "screen_2")
    object Settings: BottomItem(title = R.string.title_screen_3, iconID = Icons.Default.Settings, route = "screen_3")
    object Screen4: BottomItem(title = R.string.title_screen_4, iconID = Icons.Default.Add, route = "screen_4")
}