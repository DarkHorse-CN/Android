package com.darkhorse.android.buildlogic

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("UnstableApiUsage")
class AndroidLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            // 从版本目录统一读取 Android 构建配置
            val androidCompileSdk = libs.findVersion("androidCompileSdk").get().requiredVersion.toInt()
            val androidMinSdk = libs.findVersion("androidMinSdk").get().requiredVersion.toInt()
            val androidJavaVersionStr = libs.findVersion("androidJavaVersion").get().requiredVersion
            val androidJavaVersion = JavaVersion.toVersion(androidJavaVersionStr)

            with(pluginManager) {
                apply("com.android.library")
            }

            extensions.configure<LibraryExtension> {
                compileSdk = androidCompileSdk

                defaultConfig {
                    minSdk = androidMinSdk
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                compileOptions {
                    sourceCompatibility = androidJavaVersion
                    targetCompatibility = androidJavaVersion
                }

                @Suppress("DEPRECATION")
                buildFeatures {
                    buildConfig = false
                    aidl = false
                }

                packaging {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    }
                }
            }

            tasks.withType<KotlinCompile>().configureEach {
                compilerOptions {
                    jvmTarget.set(JvmTarget.fromTarget(androidJavaVersionStr))
                }
            }
        }
    }
}
