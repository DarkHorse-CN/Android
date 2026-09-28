package com.darkhorse.android.core.network

/**
 * HTTP 客户端抽象接口
 *
 * 定义网络请求的基础契约，解耦业务层与具体实现（Retrofit / OkHttp / Ktor）
 */
interface IHttpClient {
    /**
     * 执行 GET 请求
     */
    suspend fun <T> get(url: String, params: Map<String, String> = emptyMap()): ApiResult<T>

    /**
     * 执行 POST 请求
     */
    suspend fun <T> post(url: String, body: Any? = null): ApiResult<T>

    /**
     * 执行 PUT 请求
     */
    suspend fun <T> put(url: String, body: Any? = null): ApiResult<T>

    /**
     * 执行 DELETE 请求
     */
    suspend fun <T> delete(url: String, params: Map<String, String> = emptyMap()): ApiResult<T>
}
