package com.darkhorse.android.core.push

import kotlinx.coroutines.flow.Flow

/**
 * 推送服务抽象接口
 *
 * 解耦业务层与具体推送 SDK（华为 / 小米 / FCM）
 */
interface IPushService {
    /** 获取推送 Token */
    val token: Flow<String>

    /** 是否已注册推送 */
    val isRegistered: Boolean

    /** 注册推送服务 */
    suspend fun register()

    /** 注销推送服务 */
    suspend fun unregister()

    /** 处理推送消息 */
    suspend fun handleMessage(data: Map<String, String>)
}
