package com.darkhorse.android.app.initializer

import android.content.Context
import androidx.startup.Initializer

/**
 * 崩溃捕获初始化器。
 *
 * 在 App Startup 阶段尽早初始化全局异常捕获，
 * 确保应用启动后任何未捕获异常都能被记录。
 */
class CrashHandlerInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        // TODO: 接入第三方崩溃捕获 SDK（如 Firebase Crashlytics、Bugly 等）
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            android.util.Log.e("CrashHandler", "Uncaught exception on thread: ${thread.name}", throwable)
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        // 崩溃捕获无需依赖其他初始化器
        return emptyList()
    }
}
