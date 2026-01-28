package com.sonalisulgadle.taskmanagerpro.presentation.navigation

sealed class Screens(val route: String) {
    object Home : Screens("home")
    object Login : Screens("login")
    object SignUp : Screens("signup")

    object EditTask : Screens("edit_task/{taskId}") {
        fun createRoute(taskId: String): String {
            return "edit_task/$taskId"
        }
    }

    object Settings : Screens("settings")

}
