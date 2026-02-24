package com.example.betteryou.domain.common

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DatastoreKeys {
    val USER_TOKEN = stringPreferencesKey("user_token")
    val DARK_THEME_KEY = booleanPreferencesKey("dark_theme")
    val USER_LANGUAGE_KEY = stringPreferencesKey("user_language")
}