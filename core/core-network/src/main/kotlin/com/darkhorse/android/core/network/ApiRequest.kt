package com.darkhorse.android.core.network

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class ApiRequest(
    val url: String,                     // URL 路径（相对路径）
    val method: String = HttpMethods.POST // 默认为 POST
)