package com.darkhorse.android.core.network

import kotlinx.coroutines.Job

interface DhRequest<R>

open class DhResponse<R>(
    val data: R?
)

suspend inline fun <reified RESP> DhRequest<RESP>.execute(): DhResponse<RESP> {
    return DhResponse(null)
}

/**
 * 请求回调 DSL 接收者
 * @param RESP 业务数据类型（对应接口返回的 data 字段）
 */
class RequestCallback<RESP> {
    internal var onLoadingAction: (() -> Unit)? = null
    internal var onSuccessAction: ((RESP) -> Unit)? = null
    internal var onErrorAction: ((Throwable) -> Unit)? = null

    fun onLoading(block: () -> Unit) {
        onLoadingAction = block
    }

    fun onSuccess(block: (RESP) -> Unit) {
        onSuccessAction = block
    }

    fun onError(block: (Throwable) -> Unit) {
        onErrorAction = block
    }
}

fun <RESP> DhRequest<RESP>.execute(block: RequestCallback<RESP>.() -> Unit): Job {
    val callback = RequestCallback<RESP>().apply(block)

    // 先触发 Loading 回调（确保在主线程）
    callback.onLoadingAction?.invoke()
//    callback.onSuccessAction?.invoke()
//    callback.onErrorAction?.invoke()
    return Job();
}
