package com.sonalisulgadle.taskmanagerpro.presentation.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sonalisulgadle.taskmanagerpro.presentation.auth.LoginScreen
import com.sonalisulgadle.taskmanagerpro.presentation.auth.LoginState
import com.sonalisulgadle.taskmanagerpro.presentation.auth.LoginViewModel
import com.sonalisulgadle.taskmanagerpro.presentation.auth.SignUpScreen
import com.sonalisulgadle.taskmanagerpro.presentation.home.HomeScreen
import com.sonalisulgadle.taskmanagerpro.presentation.task.editTask.EditTaskScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    authViewModel: LoginViewModel = hiltViewModel()
) {

    val authState by authViewModel.state.collectAsState()

    Scaffold { contentPadding ->
        NavHost(
            navController = navController,
            startDestination = when (authState) {
                is LoginState.LoggedIn -> Screens.Home.route
                is LoginState.LoggedOut -> Screens.Login.route
                else -> Screens.Login.route
            }
        ) {
            composable(route = Screens.Login.route) {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(Screens.Home.route) {
                            popUpTo(Screens.Login.route) { inclusive = true }
                        }
                    },
                    onSignUpClick = {
                        navController.navigate(Screens.SignUp.route) {
                            popUpTo(Screens.Login.route) { inclusive = true }
                        }
                    })
            }
            composable(route = Screens.SignUp.route) {
                SignUpScreen(onSignUpSuccess = {
                    navController.navigate(Screens.Home.route) {
                        popUpTo(Screens.SignUp.route) { inclusive = true }
                    }
                })
            }
            composable(route = Screens.Home.route) {
                HomeScreen(
                    onTaskCardClick = { taskId ->
                        navController.navigate(Screens.EditTask.createRoute(taskId))
                    }
                )
            }
            composable(
                route = Screens.EditTask.route,
                arguments = listOf(
                    navArgument("taskId") {
                        type = NavType.StringType
                    }
                )) { backStackEntry ->
                val taskId = backStackEntry.arguments?.getString("taskId").orEmpty()
                EditTaskScreen(taskId = taskId, onBackPressed = {
                    navController.popBackStack()
                })

            }
        }

    }
}