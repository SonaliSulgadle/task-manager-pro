package com.sonalisulgadle.taskmanagerpro.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.sonalisulgadle.taskmanagerpro.domain.usecase.login.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private var _state = MutableStateFlow<LoginState>(LoginState.Idle)
    val state = _state

    init {
        val user = FirebaseAuth.getInstance().currentUser
        _state.value = if (user != null) {
            LoginState.LoggedIn(user)
        } else {
            LoginState.LoggedOut
        }
    }

    internal fun onLoginWithEmail(email: String, password: String) {
        viewModelScope.launch {
            _state.value = LoginState.Loading
            val result = loginUseCase.signInEmail(email, password)
            _state.value = if (result.isSuccess) {
                LoginState.Success
            } else {
                LoginState.Error(result.exceptionOrNull()?.message)
            }
        }
    }

    internal fun onSignUpWithEmail(email: String, password: String, confirmPassword: String) {
        if (password != confirmPassword) {
            _state.value = LoginState.Error("Password does not match")
            return
        }
        viewModelScope.launch {
            _state.value = LoginState.Loading
            val result = loginUseCase.signUpEmail(email, password)
            _state.value = if (result.isSuccess) {
                LoginState.Success
            } else {
                LoginState.Error(result.exceptionOrNull()?.message)
            }
        }
    }

    internal fun onSignInWithGoogleClick(idToken: String) {
        viewModelScope.launch {
            _state.value = LoginState.Loading
            val result = loginUseCase.signInGoogle(idToken)
            _state.value = if (result.isSuccess) {
                LoginState.Success
            } else {
                LoginState.Error(result.exceptionOrNull()?.message)
            }
        }
    }

    internal fun resetState() {
        _state.update { LoginState.Idle }
    }
}