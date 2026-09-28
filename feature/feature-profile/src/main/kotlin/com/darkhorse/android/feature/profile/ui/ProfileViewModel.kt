package com.darkhorse.android.feature.profile.ui

import androidx.lifecycle.ViewModel
import com.darkhorse.android.feature.login.data.AuthRepository
import com.darkhorse.android.feature.login.data.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    authRepository: AuthRepository,
) : ViewModel() {

    val isLoggedIn: StateFlow<Boolean> = authRepository.isLoggedIn
    val userInfo: StateFlow<UserInfo?> = authRepository.userInfo
    private val _logoutCallback = authRepository

    fun logout() {
        _logoutCallback.logout()
    }
}
