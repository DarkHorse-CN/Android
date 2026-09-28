package com.darkhorse.android.core.router

/**
 * 路由抽象接口——定义导航的基本操作
 *
 * 位于 core:router 中，所有 feature 模块依赖此接口，
 * 避免直接依赖具体的导航实现。
 *
 * 实现由 lib:lib-router 提供（Navigation Compose / 其他实现）
 */
interface Router {
    /**
     * 导航到指定目标
     *
     * @param destination 目标路由对象
     * @param options 导航选项（由具体实现定义）
     */
    fun navigateTo(destination: Any, options: Any? = null)

    /**
     * 弹出返回栈
     */
    fun popBackStack()

    /**
     * 弹出到指定目标
     *
     * @param destination 目标路由
     * @param inclusive 是否包含目标路由
     */
    fun popTo(destination: Any, inclusive: Boolean = false)

    /**
     * 获取当前路由
     */
    fun currentRoute(): Any?
}

/**
 * 路由扩展函数抽象——定义如何在导航图中注册路由
 *
 * 每个 feature 模块通过此接口扩展导航图
 */
interface NavGraphBuilderExtensions {
    /**
     * 向导航图添加路由
     */
    fun addRoutes(builder: Any)
}

/**
 * 路由工具类抽象
 *
 * 提供便捷的路由操作方法
 */
interface RouterUtils {
    /**
     * 创建带参数的路由
     */
    fun <T : Any> createRoute(route: T): T

    /**
     * 检查是否可以返回到指定路由
     */
    fun canNavigateTo(destination: Any): Boolean
}
