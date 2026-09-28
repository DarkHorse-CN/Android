package com.darkhorse.android.core.network

import kotlinx.coroutines.flow.Flow

/**
 * 网络状态监听接口
 */
interface INetworkMonitor {
    /** 当前是否联网 */
    val isOnline: Boolean

    /** 网络状态变化流 */
    val networkState: Flow<NetworkState>
}

/**
 * 网络连接状态
 */
sealed interface NetworkState {
    data object Connected : NetworkState
    data object Disconnected : NetworkState
    data class Metered(val type: String) : NetworkState
}
