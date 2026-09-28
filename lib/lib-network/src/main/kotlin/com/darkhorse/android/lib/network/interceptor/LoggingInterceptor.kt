package com.darkhorse.android.lib.network.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

/**
 * OkHttp 日志拦截器 — 打印请求/响应日志
 *
 * 位于 lib:lib-network-okhttp，是 core:core-network 中 INetworkMonitor 的具体实现辅助
 */
class LoggingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        Log.d(TAG, "➡️ ${request.method} ${request.url}")

        val response = chain.proceed(request)

        Log.d(TAG, "⬅️ ${response.code} ${response.request.url}")

        return response
    }

    companion object {
        private const val TAG = "OkHttp"
    }
}
