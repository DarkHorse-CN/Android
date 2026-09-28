package com.darkhorse.android.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * Feature 模块约定插件
 *
 * 统一配置所有 feature 模块的公共依赖：
 * - Android Library + Compose + Hilt（通过已有插件）
 * - 核心抽象层模块（core:core-* 接口层）
 * - AndroidX Lifecycle + Navigation
 * - 测试基础设施
 *
 * 架构原则：Feature 模块只依赖 core 层的抽象接口，不依赖 lib 层的具体实现。
 * lib 层的实现通过 app 模块的 DI 组装注入。
 */
@Suppress("UnstableApiUsage")
class FeatureConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("myapp.android.library")
                apply("myapp.android.compose")
                apply("myapp.android.hilt")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                // ── Core 抽象层（接口契约） ──
                add("implementation", project(":core:core-common"))
                add("implementation", project(":core:core-ui"))
                add("implementation", project(":core:core-network"))
                add("implementation", project(":core:core-router"))
                add("implementation", project(":core:core-image"))
                add("implementation", project(":core:core-log"))
                add("implementation", project(":core:core-permission"))
                add("implementation", project(":core:core-storage"))
                add("implementation", project(":core:core-analytics"))

                // ── AndroidX ──
                add("implementation", libs.findLibrary("androidx-core-ktx").get())
                add("implementation", libs.findLibrary("androidx-lifecycle-runtime-ktx").get())
                add("implementation", libs.findLibrary("androidx-lifecycle-runtime-compose").get())
                add("implementation", libs.findLibrary("androidx-lifecycle-viewmodel-compose").get())
                add("implementation", libs.findLibrary("androidx-navigation-compose").get())
                add("implementation", libs.findLibrary("androidx-activity-compose").get())

                // ── Hilt Navigation ──
                add("implementation", libs.findLibrary("hilt-navigation-compose").get())

                // ── Compose ──
                add("implementation", libs.findLibrary("compose-icons-core").get())
                // Compose Foundation (HorizontalPager, 动画, 手势等)
                add("implementation", "androidx.compose.foundation:foundation")

                // ── Coil (Image Loading) ──
                add("implementation", libs.findLibrary("coil-compose").get())

                // ── Kotlin Serialization ──
                add("implementation", libs.findLibrary("kotlinx-serialization-json").get())

                // ── Testing ──
                add("testImplementation", libs.findLibrary("junit").get())
                add("testImplementation", libs.findLibrary("kotlinx-coroutines-test").get())
                add("testImplementation", libs.findLibrary("mockk").get())
                add("testImplementation", libs.findLibrary("turbine").get())
                add("androidTestImplementation", libs.findLibrary("androidx-test-ext-junit").get())
                add("androidTestImplementation", libs.findLibrary("androidx-compose-ui-test-junit4").get())
            }
        }
    }
}
