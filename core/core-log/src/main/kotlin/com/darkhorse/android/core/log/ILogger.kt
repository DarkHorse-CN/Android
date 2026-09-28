package com.darkhorse.android.core.log

/**
 * 日志抽象接口
 *
 * 解耦业务层与具体日志库（Timber / Logcat / Firebase Crashlytics）
 */
interface ILogger {
    fun v(tag: String, message: String, throwable: Throwable? = null)
    fun d(tag: String, message: String, throwable: Throwable? = null)
    fun i(tag: String, message: String, throwable: Throwable? = null)
    fun w(tag: String, message: String, throwable: Throwable? = null)
    fun e(tag: String, message: String, throwable: Throwable? = null)

    /** 记录崩溃日志 */
    fun crash(throwable: Throwable, tag: String? = null)
}

/**
 * 默认 Tag 日志扩展
 */
fun ILogger.v(message: String, tag: String = "App") = v(tag, message)
fun ILogger.d(message: String, tag: String = "App") = d(tag, message)
fun ILogger.i(message: String, tag: String = "App") = i(tag, message)
fun ILogger.w(message: String, tag: String = "App") = w(tag, message)
fun ILogger.e(message: String, tag: String = "App") = e(tag, message)
