package com.sonalisulgadle.taskmanagerpro.presentation.navigation

sealed class Screens(val route: String) {
    object Home : Screens("Home")
    object Login : Screens("Login")
    object SignUp : Screens("SignUp")
}