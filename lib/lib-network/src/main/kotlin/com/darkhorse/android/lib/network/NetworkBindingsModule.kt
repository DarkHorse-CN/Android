package com.darkhorse.android.lib.network

import com.darkhorse.android.core.network.ApiFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * 网络层 Hilt 绑定模块 — 将接口绑定到具体实现
 *
 * OkHttpApiFactory → ApiFactory
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkBindingsModule {

    @Binds
    @Singleton
    abstract fun bindApiFactory(impl: OkHttpApiFactory): ApiFactory
}
