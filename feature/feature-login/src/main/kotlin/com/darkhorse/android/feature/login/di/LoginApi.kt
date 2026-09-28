package com.darkhorse.android.feature.login.di

import com.darkhorse.android.feature.login.network.LoginRequest
import com.darkhorse.android.feature.login.network.LoginResponse
import com.darkhorse.android.feature.login.data.RegisterRequest
import com.darkhorse.android.feature.login.data.RegisterResponse

interface LoginApi {
    suspend fun login(request: LoginRequest): LoginResponse

    suspend fun register(request: RegisterRequest): RegisterResponse
}