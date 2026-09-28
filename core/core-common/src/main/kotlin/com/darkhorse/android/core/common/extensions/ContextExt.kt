package com.darkhorse.android.core.common.extensions

import android.content.Context
import android.widget.Toast

/**
 * Context 扩展函数
 */

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

val Context.screenWidthPx: Int
    get() = resources.displayMetrics.widthPixels

val Context.screenHeightPx: Int
    get() = resources.displayMetrics.heightPixels
