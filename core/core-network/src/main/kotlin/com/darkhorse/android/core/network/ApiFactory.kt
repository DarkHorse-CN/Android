package com.darkhorse.android.core.network

/**
 * API 工厂抽象
 *
 * 提供创建 Retrofit API 接口实例的能力
 * 具体实现由 lib:lib-network-okhttp 提供
 */
interface ApiFactory {
    /**
     * 创建 API 接口实例
     *
     * @param T API 接口类型
     * @param baseUrl 基础 URL（可选，默认使用全局配置）
     */
    fun <T : Any> create(apiClass: Class<T>, baseUrl: String? = null): T
}

/** 便捷扩展 */
inline fun <reified T : Any> ApiFactory.create(baseUrl: String? = null): T {
    return create(T::class.java, baseUrl)
}
