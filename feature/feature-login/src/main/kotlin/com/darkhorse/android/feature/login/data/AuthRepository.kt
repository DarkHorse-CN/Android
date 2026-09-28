package com.darkhorse.android.feature.login.data

import android.content.Context
import com.darkhorse.android.core.network.execute
import com.darkhorse.android.feature.login.di.LoginApi
import com.darkhorse.android.feature.login.network.LoginRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val authApi: LoginApi,
    @ApplicationContext private val context: Context,
) {
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _userInfo = MutableStateFlow<UserInfo?>(null)
    val userInfo: StateFlow<UserInfo?> = _userInfo.asStateFlow()

    init {
        restoreSession()
    }

    private fun restoreSession() {
        val token = prefs.getString(KEY_TOKEN, null)
        if (token != null) {
            _isLoggedIn.value = true
            _userInfo.value = UserInfo(
                userId = prefs.getString(KEY_USER_ID, "") ?: "",
                username = prefs.getString(KEY_USERNAME, "") ?: "",
                token = token,
                email = prefs.getString(KEY_EMAIL, "") ?: "",
            )
        }
    }

    private fun saveSession(info: UserInfo) {
        prefs.edit()
            .putString(KEY_TOKEN, info.token)
            .putString(KEY_USER_ID, info.userId)
            .putString(KEY_USERNAME, info.username)
            .putString(KEY_EMAIL, info.email)
            .apply()
        _isLoggedIn.value = true
        _userInfo.value = info
    }

    private fun clearSession() {
        prefs.edit().clear().apply()
        _isLoggedIn.value = false
        _userInfo.value = null
    }

    suspend fun login(username: String, password: String): Result<UserInfo> {
        val request = LoginRequest(username = username, password = password)
        val response = request.execute()

        request.execute {
            onLoading { }
            onSuccess { }
            onError { }
        }

        return try {
            val info = UserInfo(
                userId = response.data?.userId ?: "",
                username = response.data?.username ?: "",
                token = response.data?.token ?: "",
                email = "",
            )
            saveSession(info)
            Result.Success(info)
        } catch (e: Exception) {
            Result.Error(
                message = e.message ?: "网络连接失败，请检查网络后重试",
                throwable = e,
            )
        }
    }

    suspend fun register(
        username: String,
        email: String,
        password: String,
    ): Result<UserInfo> {
        return try {
            val response = authApi.register(
                RegisterRequest(
                    username = username,
                    email = email,
                    password = password,
                )
            )
            val info = UserInfo(
                userId = response.userId,
                username = response.username,
                token = response.token,
                email = response.email,
            )
            saveSession(info)
            Result.Success(info)
        } catch (e: Exception) {
            Result.Error(
                message = e.message ?: "注册失败，请稍后重试",
                throwable = e,
            )
        }
    }

    fun logout() {
        clearSession()
    }

    companion object {
        private const val PREFS_NAME = "auth_prefs"
        private const val KEY_TOKEN = "token"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USERNAME = "username"
        private const val KEY_EMAIL = "email"
    }
}

data class UserInfo(
    val userId: String = "",
    val username: String = "",
    val token: String = "",
    val email: String = "",
)
