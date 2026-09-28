package com.darkhorse.android.app.initializer

import android.content.Context
import androidx.startup.Initializer

/**
 * WorkManager 初始化器。
 *
 * 在 App Startup 阶段初始化 WorkManager，
 * 确保后台任务调度在应用启动时就可用。
 *
 * 依赖打点初始化完成后再执行。
 */
class WorkManagerInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        // TODO: 接入 WorkManager 后在此初始化
        // androidx.work.Configuration.Builder().setMinimumLoggingLevel(android.util.Log.INFO).build()
        // WorkManager.initialize(context, configuration)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(AnalyticsInitializer::class.java)
    }
}
