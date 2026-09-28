package com.darkhorse.android.feature.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darkhorse.android.feature.login.data.Result
import com.darkhorse.android.feature.login.data.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _usernameError = MutableStateFlow<String?>(null)
    val usernameError: StateFlow<String?> = _usernameError.asStateFlow()

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> = _passwordError.asStateFlow()

    /**
     * 校验并执行登录
     */
    fun login(username: String, password: String) {
        // 先清除旧错误
        _usernameError.value = null
        _passwordError.value = null

        // 字段级校验
        var hasError = false

        if (username.isBlank()) {
            _usernameError.value = "请输入用户名"
            hasError = true
        } else if (username.length < 3) {
            _usernameError.value = "用户名至少需要 3 个字符"
            hasError = true
        }

        if (password.isBlank()) {
            _passwordError.value = "请输入密码"
            hasError = true
        } else if (password.length < 6) {
            _passwordError.value = "密码至少需要 6 个字符"
            hasError = true
        }

        if (hasError) return

        _uiState.value = LoginUiState.Loading
        viewModelScope.launch {
            val result = try {
                authRepository.login(username, password)
            } catch (e: Exception) {
                Result.Error(e.message ?: "登录失败，请稍后重试")
            }
            when (result) {
                is Result.Success -> {
                    _uiState.value = LoginUiState.Success
                }
                is Result.Error -> {
                    _uiState.value = LoginUiState.Error(result.message)
                }
            }
        }
    }

    fun resetState() {
        _uiState.value = LoginUiState.Idle
        _usernameError.value = null
        _passwordError.value = null
    }
}
