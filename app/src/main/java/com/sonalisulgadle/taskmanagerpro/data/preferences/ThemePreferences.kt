package com.sonalisulgadle.taskmanagerpro.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sonalisulgadle.taskmanagerpro.domain.model.AppTheme
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemePreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val Context.DataStore by preferencesDataStore("settings")

    val themeMode: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[THEME_MODE] ?: AppTheme.SYSTEM.name
    }

    suspend fun setThemeMode(theme: AppTheme) {
        context.dataStore.edit { preferences ->
            preferences[THEME_MODE] = theme.name
        }
    }

    companion object {
        private val THEME_MODE = stringPreferencesKey("app_theme")
    }
}

private val Context.dataStore by preferencesDataStore(name = "theme_preferences")