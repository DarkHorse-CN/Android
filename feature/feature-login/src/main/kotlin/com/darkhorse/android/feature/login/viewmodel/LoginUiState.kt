package com.darkhorse.android.feature.login.viewmodel

/**
 * 登录页 UI 状态
 */
sealed interface LoginUiState {
    /** 初始/空闲 */
    data object Idle : LoginUiState

    /** 加载中（登录请求进行中） */
    data object Loading : LoginUiState

    /** 登录成功 */
    data object Success : LoginUiState

    /** 登录失败 */
    data class Error(val message: String) : LoginUiState
}
