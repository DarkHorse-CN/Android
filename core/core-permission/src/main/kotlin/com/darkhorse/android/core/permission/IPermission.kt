package com.darkhorse.android.core.permission

import android.Manifest

/**
 * 权限抽象接口
 *
 * 统一管理运行时权限请求逻辑
 */
interface IPermission {
    /**
     * 检查权限是否已授予
     */
    fun isGranted(permission: String): Boolean

    /**
     * 请求权限
     *
     * @param permissions 需要请求的权限列表
     * @param onResult 回调 (permission: String, granted: Boolean) -> Unit
     */
    suspend fun request(
        permissions: List<String>,
        onResult: (Map<String, Boolean>) -> Unit,
    )

    /**
     * 是否需要显示权限说明（用户已拒绝过）
     */
    fun shouldShowRationale(permission: String): Boolean

    companion object {
        val CAMERA = Manifest.permission.CAMERA
        val LOCATION_FINE = Manifest.permission.ACCESS_FINE_LOCATION
        val LOCATION_COARSE = Manifest.permission.ACCESS_COARSE_LOCATION
        val STORAGE_READ = Manifest.permission.READ_EXTERNAL_STORAGE
        val STORAGE_WRITE = Manifest.permission.WRITE_EXTERNAL_STORAGE
        val POST_NOTIFICATIONS = Manifest.permission.POST_NOTIFICATIONS
    }
}
