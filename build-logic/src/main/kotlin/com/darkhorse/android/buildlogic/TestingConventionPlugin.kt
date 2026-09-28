package com.darkhorse.android.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * 测试约定插件
 *
 * 为测试模块统一配置测试框架依赖：
 * - JUnit 4
 * - Kotlin Coroutines Test
 * - MockK
 * - Turbine (Flow 测试)
 * - AndroidX Test
 */
@Suppress("UnstableApiUsage")
class TestingConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                add("implementation", libs.findLibrary("junit").get())
                add("implementation", libs.findLibrary("kotlinx-coroutines-test").get())
                add("implementation", libs.findLibrary("mockk").get())
                add("implementation", libs.findLibrary("turbine").get())
                add("implementation", libs.findLibrary("androidx-test-core").get())
            }
        }
    }
}
