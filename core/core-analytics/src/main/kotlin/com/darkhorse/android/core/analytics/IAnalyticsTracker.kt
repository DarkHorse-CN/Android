package com.darkhorse.android.core.analytics

/**
 * 埋点/分析抽象接口
 *
 * 解耦业务层与具体分析 SDK（Firebase / Umeng / Mixpanel）
 */
interface IAnalyticsTracker {
    /** 记录自定义事件 */
    fun logEvent(name: String, params: Map<String, Any> = emptyMap())

    /** 记录屏幕浏览 */
    fun logScreenView(screenName: String, screenClass: String? = null)

    /** 记录用户属性 */
    fun setUserProperty(name: String, value: String)

    /** 设置用户 ID（登录后） */
    fun setUserId(userId: String?)

    /** 重置用户（登出后） */
    fun reset()
}
