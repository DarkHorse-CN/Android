package com.darkhorse.android.feature.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.darkhorse.android.feature.profile.ui.ProfileScreen
import kotlinx.serialization.Serializable

// ─── Type-Safe Route Definitions ───

@Serializable
object ProfileRoute

// ─── NavGraphBuilder Extensions ───

fun NavGraphBuilder.profileScreen(
    onNavigateToLogin: () -> Unit = {},
) {
    composable<ProfileRoute> {
        ProfileScreen(
            onNavigateToLogin = onNavigateToLogin,
        )
    }
}

// ─── NavController Extensions ───

fun NavController.navigateToProfile(navOptions: NavOptions? = null) {
    navigate(ProfileRoute, navOptions)
}
