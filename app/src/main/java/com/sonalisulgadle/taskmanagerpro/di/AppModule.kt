package com.sonalisulgadle.taskmanagerpro.di

import com.google.firebase.auth.FirebaseAuth
import com.sonalisulgadle.taskmanagerpro.data.repository.AuthRepositoryImpl
import com.sonalisulgadle.taskmanagerpro.domain.repository.AuthRepository
import com.sonalisulgadle.taskmanagerpro.domain.usecase.login.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(auth: FirebaseAuth): AuthRepository = AuthRepositoryImpl(auth)

    @Provides
    fun provideLoginUseCase(authRepository: AuthRepository) = LoginUseCase(authRepository)
}