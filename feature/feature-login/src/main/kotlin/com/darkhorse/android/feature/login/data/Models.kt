package com.darkhorse.android.feature.login.data

import com.darkhorse.android.core.network.ApiRequest
import com.darkhorse.android.core.network.DhRequest
import com.darkhorse.android.feature.login.network.LoginApi

@ApiRequest(LoginApi.API_REGISTER)
data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
) : DhRequest<RegisterResponse>

data class RegisterResponse(
    val token: String,
    val userId: String,
    val username: String,
    val email: String,
)
