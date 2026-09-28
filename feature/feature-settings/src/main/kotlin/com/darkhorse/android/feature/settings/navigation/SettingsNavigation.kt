package com.darkhorse.android.feature.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.darkhorse.android.feature.settings.ui.SettingsScreen
import kotlinx.serialization.Serializable

// ─── Type-Safe Route Definitions ───

@Serializable
object SettingsRoute

// ─── NavGraphBuilder Extensions ───

/**
 * 在 NavGraph 中注册设置页路由
 */
fun NavGraphBuilder.settingsScreen(
    onNavigateBack: () -> Unit,
) {
    composable<SettingsRoute> {
        SettingsScreen(onNavigateBack = onNavigateBack)
    }
}

// ─── NavController Extensions ───

fun NavController.navigateToSettings(navOptions: NavOptions? = null) {
    navigate(SettingsRoute, navOptions)
}
