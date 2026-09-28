package com.darkhorse.android.feature.main.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Feature-Main 的 Hilt 绑定。
 *
 * 大部分依赖由 Hilt 自动注入，
 * 此 Module 仅用于管理 feature-main 特有的绑定。
 */
@Module
@InstallIn(SingletonComponent::class)
object MainModule {
    // 当前无额外绑定；依赖由 Hilt @Inject / @HiltViewModel 自动注入
}
