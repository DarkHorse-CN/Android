package com.darkhorse.android.core.notification

import android.app.NotificationChannel
import android.app.NotificationManager

/**
 * 通知管理抽象接口
 *
 * 封装系统 NotificationManager 的操作
 */
interface INotificationManager {
    /** 创建通知渠道 */
    fun createChannel(channel: NotificationChannel)

    /** 发送通知 */
    fun notify(id: Int, notification: android.app.Notification)

    /** 取消通知 */
    fun cancel(id: Int)

    /** 取消所有通知 */
    fun cancelAll()

    /** 通知渠道是否已启用 */
    fun isChannelEnabled(channelId: String): Boolean

    /** 打开通知设置页 */
    fun openNotificationSettings()
}
