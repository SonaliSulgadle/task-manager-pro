package com.sonalisulgadle.taskmanagerpro.domain.usecase.preferences

import com.sonalisulgadle.taskmanagerpro.data.preferences.ThemePreferences
import com.sonalisulgadle.taskmanagerpro.domain.model.AppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetThemeUseCase @Inject constructor(
    private val preferences: ThemePreferences
) {
    operator fun invoke(): Flow<AppTheme> = preferences.themeMode.map {
        runCatching {
            AppTheme.valueOf(it)
        }.getOrElse {
            AppTheme.SYSTEM
        }
    }
}