package com.sonalisulgadle.taskmanagerpro.presentation.preferences

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonalisulgadle.taskmanagerpro.domain.model.AppTheme
import com.sonalisulgadle.taskmanagerpro.domain.repository.AuthRepository
import com.sonalisulgadle.taskmanagerpro.domain.usecase.preferences.GetThemeUseCase
import com.sonalisulgadle.taskmanagerpro.domain.usecase.preferences.SetThemeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    getThemeUseCase: GetThemeUseCase,
    private val setThemeUseCase: SetThemeUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {

    val theme: StateFlow<AppTheme> = getThemeUseCase().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        AppTheme.SYSTEM
    )

    internal fun setTheme(theme: AppTheme) {
        viewModelScope.launch {
            setThemeUseCase(theme)
        }
    }

    internal fun signOut() {
        authRepository.signOut()
    }
}