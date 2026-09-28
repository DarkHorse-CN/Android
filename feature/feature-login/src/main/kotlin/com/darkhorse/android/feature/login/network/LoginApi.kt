package com.darkhorse.android.feature.login.network

import com.darkhorse.android.core.network.ApiRequest
import com.darkhorse.android.core.network.DhRequest

object LoginApi {
    const val API_LOGIN = "/login"
    const val API_REGISTER = "/register"
}

@ApiRequest(LoginApi.API_LOGIN)
data class LoginRequest(
    val username: String,
    val password: String,
) : DhRequest<LoginResponse>

data class LoginResponse(
    val token: String,
    val userId: String,
    val username: String,
)
