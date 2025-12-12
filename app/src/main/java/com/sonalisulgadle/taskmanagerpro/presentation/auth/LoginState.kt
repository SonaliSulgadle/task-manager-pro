package com.sonalisulgadle.taskmanagerpro.presentation.auth

import com.google.firebase.auth.FirebaseUser

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String?) : LoginState()
    data class LoggedIn(val user: FirebaseUser?) : LoginState()
    object LoggedOut : LoginState()
}