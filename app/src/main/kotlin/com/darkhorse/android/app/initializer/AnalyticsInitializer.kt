package com.darkhorse.android.app.initializer

import android.content.Context
import androidx.startup.Initializer

/**
 * 打点/分析初始化器。
 *
 * 初始化数据采集 SDK，需在 [CrashHandlerInitializer] 之后执行。
 */
class AnalyticsInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        // TODO: 接入分析 SDK（如 Firebase Analytics、Umeng 等）
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        // 依赖崩溃捕获先初始化
        return listOf(CrashHandlerInitializer::class.java)
    }
}
