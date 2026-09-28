package com.darkhorse.android.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

@Suppress("UnstableApiUsage")
class ComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            val composeBomVersion = libs.findVersion("androidxComposeBom").get().requiredVersion

            with(pluginManager) {
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            dependencies {
                // Compose BOM — 所有 Compose 库版本由 BOM 统一管理
                add("implementation", platform("androidx.compose:compose-bom:$composeBomVersion"))
                add("implementation", libs.findLibrary("androidx-compose-material3").get())
                add("implementation", libs.findLibrary("androidx-compose-ui").get())
                add("implementation", libs.findLibrary("androidx-compose-ui-tooling-preview").get())
                add("debugImplementation", libs.findLibrary("androidx-compose-ui-tooling").get())
                add("debugImplementation", libs.findLibrary("androidx-compose-ui-test-manifest").get())
            }
        }
    }
}
