package by.bsuir.makogon.alina.navigation.bottom_navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import by.bsuir.makogon.alina.navigation.bottom_navigation.EventsNavGraphConstants.EVENT_ID_KEY
import by.bsuir.makogon.alina.navigation.bottom_navigation.screens.AboutAppScreen
import by.bsuir.makogon.alina.navigation.bottom_navigation.screens.EditEventScreen
import by.bsuir.makogon.alina.navigation.bottom_navigation.screens.HomeScreen
import by.bsuir.makogon.alina.navigation.bottom_navigation.screens.Screen4
import by.bsuir.makogon.alina.navigation.bottom_navigation.screens.SettingsScreen
import java.util.UUID

@Composable
fun NavGraph(
    navController: NavHostController,
    darkTheme: Boolean,
    onThemeUpdated: () -> Unit,
    eventId:UUID?=UUID.randomUUID()
) {
    NavHost(
        navController = navController,
        startDestination = BottomItem.Screen4.route,
        modifier = Modifier,
    ) {
        composable(route = BottomItem.Home.route) {
            HomeScreen(navController)
        }
        composable(route = BottomItem.Profile.route) {
            AboutAppScreen()
        }
        composable(route = BottomItem.Settings.route) {
            SettingsScreen(darkTheme, onThemeUpdated)
        }
        composable(route = BottomItem.Screen4.route) {
            Screen4()
        }
        composable(
            route = Route.EditEventScreen.route + "/{$EVENT_ID_KEY}",
            arguments = listOf(
                navArgument(
                    name = EVENT_ID_KEY
                )
                {
                    type = NavType.StringType
                }
            )
        ) {
         EditEventScreen(navController = navController, eventId = eventId)
        }
    }
}

object EventsNavGraphConstants {
    const val EVENT_ID_KEY = "eventId"
}