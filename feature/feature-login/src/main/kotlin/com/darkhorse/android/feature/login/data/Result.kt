package com.darkhorse.android.feature.login.data

/**
 * 操作结果封装。
 * 取代 [com.darkhorse.android.core.model.Result] 的本地版，避免对 core:core-model 的依赖。
 */
sealed class Result<out T> {

    data class Success<T>(val data: T) : Result<T>()

    data class Error(
        val message: String,
        val throwable: Throwable? = null,
    ) : Result<Nothing>()
}
