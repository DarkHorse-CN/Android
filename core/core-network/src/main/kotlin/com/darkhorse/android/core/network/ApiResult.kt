package com.darkhorse.android.core.network

/**
 * 网络请求结果的密封类型
 *
 * 强制调用方通过 when 表达式处理所有分支，编译安全。
 * [Loading] 用于 Flow 场景发射中间状态。
 */
sealed interface ApiResult<out T> {

    /** 请求成功 */
    data class Success<T>(val data: T) : ApiResult<T>

    /** 服务器返回业务错误（4xx/5xx） */
    data class Error(val code: Int, val message: String) : ApiResult<Nothing>

    /** 请求过程发生异常（网络断开、超时、解析失败） */
    data class Exception(val throwable: Throwable) : ApiResult<Nothing>

    /** 请求进行中（仅 Flow 可发射此状态） */
    data object Loading : ApiResult<Nothing>
}

/** 当次结果是否为成功 */
fun ApiResult<*>.isSuccess(): Boolean = this is ApiResult.Success

/** 当次结果是否为错误或异常 */
fun ApiResult<*>.isError(): Boolean = this is ApiResult.Error || this is ApiResult.Exception

/** 当次结果是否为加载中 */
fun ApiResult<*>.isLoading(): Boolean = this is ApiResult.Loading

/** 获取成功数据（失败时返回 null） */
fun <T> ApiResult<T>.getOrNull(): T? = (this as? ApiResult.Success)?.data

/** 获取成功数据（失败时返回默认值） */
fun <T> ApiResult<T>.getOrDefault(default: @UnsafeVariance T): T = getOrNull() ?: default

/** 转换为 Result<T> 标准库类型 */
fun <T> ApiResult<T>.toStdlibResult(): Result<T> = when (this) {
    is ApiResult.Success -> Result.success(data)
    is ApiResult.Error -> Result.failure(RuntimeException("[$code] $message"))
    is ApiResult.Exception -> Result.failure(throwable)
    is ApiResult.Loading -> Result.failure(IllegalStateException("Request still loading"))
}
