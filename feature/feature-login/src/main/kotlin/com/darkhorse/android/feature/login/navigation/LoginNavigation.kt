package com.darkhorse.android.feature.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.darkhorse.android.feature.login.ui.LoginScreen
import com.darkhorse.android.feature.login.ui.RegisterScreen
import kotlinx.serialization.Serializable

// ─── Type-Safe Route Definitions ───

@Serializable
object LoginRoute

@Serializable
object RegisterRoute

// ─── NavGraphBuilder Extensions ───

fun NavGraphBuilder.loginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
) {
    composable<LoginRoute> {
        LoginScreen(
            onLoginSuccess = onLoginSuccess,
            onNavigateToRegister = onNavigateToRegister,
        )
    }
}

fun NavGraphBuilder.registerScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    composable<RegisterRoute> {
        RegisterScreen(
            onRegisterSuccess = onRegisterSuccess,
            onNavigateBack = onNavigateBack,
        )
    }
}

// ─── NavController Extensions ───

fun NavController.navigateToLogin(navOptions: NavOptions? = null) {
    navigate(LoginRoute, navOptions)
}

fun NavController.navigateToRegister(navOptions: NavOptions? = null) {
    navigate(RegisterRoute, navOptions)
}
