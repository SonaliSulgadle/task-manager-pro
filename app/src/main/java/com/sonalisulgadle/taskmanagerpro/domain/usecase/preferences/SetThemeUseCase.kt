package com.sonalisulgadle.taskmanagerpro.domain.usecase.preferences

import com.sonalisulgadle.taskmanagerpro.data.preferences.ThemePreferences
import com.sonalisulgadle.taskmanagerpro.domain.model.AppTheme
import javax.inject.Inject

class SetThemeUseCase @Inject constructor(
    private val preferences: ThemePreferences
) {
    suspend operator fun invoke(theme: AppTheme) {
        preferences.setThemeMode(theme)
    }
}