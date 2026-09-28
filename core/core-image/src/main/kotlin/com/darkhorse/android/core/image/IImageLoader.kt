package com.darkhorse.android.core.image

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale

/**
 * 图片加载抽象接口
 *
 * 解耦 UI 层与具体图片加载库（Coil / Glide / Fresco）
 */
interface IImageLoader {
    /**
     * 加载图片到 ImageView
     *
     * @param url 图片 URL
     * @param imageView 目标 ImageView（XML 布局使用）
     * @param placeholder 占位图
     * @param error 加载失败图
     * @param crossFade 是否启用淡入动画
     */
    fun load(
        url: String,
        imageView: ImageView,
        placeholder: Drawable? = null,
        error: Drawable? = null,
        crossFade: Boolean = true,
    )

    /**
     * 加载 Compose Painter
     *
     * @param url 图片 URL
     * @param contentScale 缩放模式
     */
    suspend fun loadComposable(
        url: String,
        contentScale: ContentScale = ContentScale.Fit,
    ): Painter?
}
