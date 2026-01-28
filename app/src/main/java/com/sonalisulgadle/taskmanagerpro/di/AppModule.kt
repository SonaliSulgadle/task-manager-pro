package com.sonalisulgadle.taskmanagerpro.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sonalisulgadle.taskmanagerpro.data.preferences.ThemePreferences
import com.sonalisulgadle.taskmanagerpro.data.repository.AuthRepositoryImpl
import com.sonalisulgadle.taskmanagerpro.data.repository.TaskRepositoryImpl
import com.sonalisulgadle.taskmanagerpro.domain.repository.AuthRepository
import com.sonalisulgadle.taskmanagerpro.domain.repository.TaskRepository
import com.sonalisulgadle.taskmanagerpro.domain.usecase.login.LoginUseCase
import com.sonalisulgadle.taskmanagerpro.domain.usecase.preferences.GetThemeUseCase
import com.sonalisulgadle.taskmanagerpro.domain.usecase.preferences.SetThemeUseCase
import com.sonalisulgadle.taskmanagerpro.domain.usecase.task.AddTaskUseCase
import com.sonalisulgadle.taskmanagerpro.domain.usecase.task.DeleteTaskUseCase
import com.sonalisulgadle.taskmanagerpro.domain.usecase.task.GetTaskByIdUseCase
import com.sonalisulgadle.taskmanagerpro.domain.usecase.task.ObserveTasksUseCase
import com.sonalisulgadle.taskmanagerpro.domain.usecase.task.UpdateTaskUseCase
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
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(auth: FirebaseAuth): AuthRepository = AuthRepositoryImpl(auth)

    @Provides
    @Singleton
    fun provideTaskRepository(auth: FirebaseAuth, firestore: FirebaseFirestore): TaskRepository =
        TaskRepositoryImpl(auth, firestore)

    @Provides
    fun provideLoginUseCase(authRepository: AuthRepository) = LoginUseCase(authRepository)

    @Provides
    fun provideObserveTaskUseCase(taskRepository: TaskRepository) =
        ObserveTasksUseCase(taskRepository)

    @Provides
    fun provideAddTaskUseCase(taskRepository: TaskRepository) =
        AddTaskUseCase(taskRepository)

    @Provides
    fun provideUpdateTaskUseCase(taskRepository: TaskRepository) =
        UpdateTaskUseCase(taskRepository)

    @Provides
    fun provideDeleteTaskUseCase(taskRepository: TaskRepository) =
        DeleteTaskUseCase(taskRepository)

    @Provides
    fun provideGetTaskByIdUseCase(taskRepository: TaskRepository) =
        GetTaskByIdUseCase(taskRepository)

    @Provides
    fun provideGetThemeUseCase(preferences: ThemePreferences) =
        GetThemeUseCase(preferences)

    @Provides
    fun provideSetThemeUseCase(preferences: ThemePreferences) =
        SetThemeUseCase(preferences)
}