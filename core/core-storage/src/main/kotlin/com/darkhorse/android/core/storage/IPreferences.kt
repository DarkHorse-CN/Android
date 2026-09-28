package com.darkhorse.android.core.storage

import kotlinx.coroutines.flow.Flow

/**
 * 偏好存储抽象接口
 *
 * 用于替代 SharedPreferences / DataStore / MMKV 的具体实现
 */
interface IPreferences {
    fun getString(key: String, default: String = ""): String
    fun getInt(key: String, default: Int = 0): Int
    fun getBoolean(key: String, default: Boolean = false): Boolean
    fun getLong(key: String, default: Long = 0L): Long
    fun getFloat(key: String, default: Float = 0f): Float

    suspend fun putString(key: String, value: String)
    suspend fun putInt(key: String, value: Int)
    suspend fun putBoolean(key: String, value: Boolean)
    suspend fun putLong(key: String, value: Long)
    suspend fun putFloat(key: String, value: Float)

    suspend fun remove(key: String)
    suspend fun clear()

    /** 监听指定 key 的 String 值变化 */
    fun observeString(key: String, default: String = ""): Flow<String>
}
