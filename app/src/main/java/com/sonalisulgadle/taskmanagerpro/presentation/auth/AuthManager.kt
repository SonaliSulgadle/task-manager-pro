package com.sonalisulgadle.taskmanagerpro.presentation.auth

import com.google.firebase.auth.FirebaseAuth

object AuthManager {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    val isUserLoggedIn: Boolean
        get() = firebaseAuth.currentUser != null
}