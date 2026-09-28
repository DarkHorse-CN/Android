package com.darkhorse.android.core.network

interface HttpRequestCallBack<R> {

    fun success(response: DhResponse<R>)

    fun error()
}
