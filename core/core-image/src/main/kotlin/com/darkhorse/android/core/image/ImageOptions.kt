package com.darkhorse.android.core.image

import android.graphics.drawable.Drawable
import androidx.compose.ui.layout.ContentScale

/**
 * 图片加载选项
 */
data class ImageOptions(
    val placeholder: Drawable? = null,
    val error: Drawable? = null,
    val contentScale: ContentScale = ContentScale.Fit,
    val crossFade: Boolean = true,
    val size: ImageSize? = null,
)

data class ImageSize(
    val width: Int,
    val height: Int,
)
