package com.sonalisulgadle.taskmanagerpro.domain.usecase.login

import com.sonalisulgadle.taskmanagerpro.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun signInEmail(email: String, password: String) =
        authRepository.signInWithEmail(email, password)

    suspend fun signUpEmail(email: String, password: String) =
        authRepository.registerWithEmail(email, password)

    suspend fun signInGoogle(tokenId: String) =
        authRepository.signInWithGoogle(tokenId)
}
