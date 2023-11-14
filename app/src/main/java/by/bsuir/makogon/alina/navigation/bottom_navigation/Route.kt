package by.bsuir.makogon.alina.navigation.bottom_navigation

sealed class Route(
    val route: String
){
    object EditEventScreen: Route(route = "editEventScreen")
    object Home: Route(route = "screen_1")
    object Profile: Route(route = "screen_2")
    object Settings: Route(route = "screen_3")
    object Screen4: Route(route = "screen_4")
}
