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
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    private val _usernameError = MutableStateFlow<String?>(null)
    val usernameError: StateFlow<String?> = _usernameError.asStateFlow()

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError.asStateFlow()

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> = _passwordError.asStateFlow()

    private val _confirmPasswordError = MutableStateFlow<String?>(null)
    val confirmPasswordError: StateFlow<String?> = _confirmPasswordError.asStateFlow()

    /**
     * 校验并执行注册
     */
    fun register(
        username: String,
        email: String,
        password: String,
        confirmPassword: String,
    ) {
        // 清除旧错误
        _usernameError.value = null
        _emailError.value = null
        _passwordError.value = null
        _confirmPasswordError.value = null

        var hasError = false

        // 用户名校验
        if (username.isBlank()) {
            _usernameError.value = "请输入用户名"
            hasError = true
        } else if (username.length < 3) {
            _usernameError.value = "用户名至少需要 3 个字符"
            hasError = true
        } else if (username.length > 20) {
            _usernameError.value = "用户名不能超过 20 个字符"
            hasError = true
        }

        // 邮箱校验
        if (email.isBlank()) {
            _emailError.value = "请输入邮箱"
            hasError = true
        } else if (!isValidEmail(email)) {
            _emailError.value = "请输入有效的邮箱地址"
            hasError = true
        }

        // 密码校验
        if (password.isBlank()) {
            _passwordError.value = "请输入密码"
            hasError = true
        } else if (password.length < 6) {
            _passwordError.value = "密码至少需要 6 个字符"
            hasError = true
        } else if (password.length > 32) {
            _passwordError.value = "密码不能超过 32 个字符"
            hasError = true
        }

        // 确认密码校验
        if (confirmPassword.isBlank()) {
            _confirmPasswordError.value = "请确认密码"
            hasError = true
        } else if (password != confirmPassword) {
            _confirmPasswordError.value = "两次输入的密码不一致"
            hasError = true
        }

        if (hasError) return

        viewModelScope.launch {
            _uiState.value = RegisterUiState.Loading
            when (val result = authRepository.register(username, email, password)) {
                is Result.Success -> {
                    _uiState.value = RegisterUiState.Success
                }
                is Result.Error -> {
                    _uiState.value = RegisterUiState.Error(result.message)
                }
            }
        }
    }

    fun resetState() {
        _uiState.value = RegisterUiState.Idle
        _usernameError.value = null
        _emailError.value = null
        _passwordError.value = null
        _confirmPasswordError.value = null
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
