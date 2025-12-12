package com.sonalisulgadle.taskmanagerpro.presentation.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sonalisulgadle.taskmanagerpro.presentation.auth.LoginScreen
import com.sonalisulgadle.taskmanagerpro.presentation.auth.LoginState
import com.sonalisulgadle.taskmanagerpro.presentation.auth.LoginViewModel
import com.sonalisulgadle.taskmanagerpro.presentation.home.HomeScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    authViewModel: LoginViewModel = hiltViewModel()
) {

    val authState by authViewModel.state.collectAsState()

    Scaffold {
        NavHost(
            navController = navController,
            startDestination = when (authState) {
                is LoginState.LoggedIn -> Screens.Home.route
                is LoginState.LoggedOut -> Screens.Login.route
                else -> Screens.Login.route
            }
        ) {
            composable(route = Screens.Login.route) {
                LoginScreen(onLoginSuccess = {
                    navController.navigate(Screens.Home.route) {
                        popUpTo(Screens.Login.route) { inclusive = true }
                    }
                })
            }
            composable(route = Screens.Home.route) {
                HomeScreen()
            }
        }

    }
}