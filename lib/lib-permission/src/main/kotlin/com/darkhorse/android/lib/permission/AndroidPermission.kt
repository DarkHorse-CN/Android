package com.darkhorse.android.lib.permission

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.darkhorse.android.core.permission.IPermission
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Android 运行时权限实现
 */
@Singleton
class AndroidPermission @Inject constructor(
    private val context: Context,
) : IPermission {

    override fun isGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) ==
            PackageManager.PERMISSION_GRANTED
    }

    override suspend fun request(
        permissions: List<String>,
        onResult: (Map<String, Boolean>) -> Unit,
    ) {
        // 简单检查：返回当前的权限状态
        // 实际运行时请求需配合 Activity / Fragment 使用
        val result = permissions.associateWith { isGranted(it) }
        onResult(result)
    }

    override fun shouldShowRationale(permission: String): Boolean {
        return false // 简单实现，实际需 Activity 配合
    }
}
