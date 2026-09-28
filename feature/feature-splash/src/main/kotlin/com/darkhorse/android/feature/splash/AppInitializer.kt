package com.darkhorse.android.feature.splash

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppInitializer @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    suspend fun initialize() {
    }

    fun isFirstInstall(): Boolean {
        val prefs = context.getSharedPreferences("onboarding_prefs", Context.MODE_PRIVATE)
        return !prefs.getBoolean("onboarding_completed", false)
    }
}
