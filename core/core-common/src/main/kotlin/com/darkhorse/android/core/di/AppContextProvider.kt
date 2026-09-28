package com.darkhorse.android.core.di

import android.annotation.SuppressLint
import android.content.Context

/**
 * Application Context 提供者。
 *
 * 用于需要 Application Context 的库/工具类初始化。
 * 在 Application.onCreate 中通过 [initialize] 注入。
 */
object AppContextProvider {

    @SuppressLint("StaticFieldLeak")
    private var appContext: Context? = null

    fun initialize(context: Context) {
        appContext = context.applicationContext
    }

    fun requireContext(): Context {
        return appContext ?: throw IllegalStateException(
            "AppContextProvider 尚未初始化，请在 Application.onCreate 中调用 initialize()",
        )
    }
}
