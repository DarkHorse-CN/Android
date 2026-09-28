package com.darkhorse.android.core.storage

/**
 * 数据库抽象接口
 *
 * 用于统一 Room / SQLDelight 等数据库操作的接口
 */
interface IDatabase {
    /** 数据库是否可读 */
    val isReadable: Boolean

    /** 数据库是否可写 */
    val isWritable: Boolean

    /** 清除所有数据 */
    suspend fun clearAllTables()
}
