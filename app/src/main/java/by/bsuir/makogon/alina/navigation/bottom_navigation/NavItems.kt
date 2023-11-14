package by.bsuir.makogon.alina.navigation.bottom_navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import by.bsuir.makogon.alina.R

sealed class NavItems(var title: Int, val selectedIcon: ImageVector, val unselectedIcon: ImageVector, val route: String){
    object Home: NavItems(title = R.string.title_screen_1, selectedIcon = Icons.Filled.Home, unselectedIcon = Icons.Outlined.Home, route = "screen_1")
    object Profile: NavItems(title = R.string.title_screen_2, selectedIcon = Icons.Filled.Person, unselectedIcon = Icons.Outlined.Person, route = "screen_2")
    object Settings: NavItems(title = R.string.title_screen_3, selectedIcon = Icons.Filled.Settings, unselectedIcon = Icons.Outlined.Settings, route = "screen_3")
    object Screen4: NavItems(title = R.string.title_screen_4, selectedIcon = Icons.Filled.Add, unselectedIcon = Icons.Outlined.Add, route = "screen_4")
}
