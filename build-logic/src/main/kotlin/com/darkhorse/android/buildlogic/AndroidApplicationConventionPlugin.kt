package com.darkhorse.android.buildlogic

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

@Suppress("UnstableApiUsage")
class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            // 从版本目录统一读取 Android 构建配置
            val androidCompileSdk = libs.findVersion("androidCompileSdk").get().requiredVersion.toInt()
            val androidMinSdk = libs.findVersion("androidMinSdk").get().requiredVersion.toInt()
            val androidTargetSdk = libs.findVersion("androidTargetSdk").get().requiredVersion.toInt()
            val androidJavaVersion = JavaVersion.toVersion(
                libs.findVersion("androidJavaVersion").get().requiredVersion,
            )
            val androidApplicationId = libs.findVersion("androidApplicationId").get().requiredVersion

            with(pluginManager) {
                apply("com.android.application")
            }

            extensions.configure<ApplicationExtension> {
                compileSdk = androidCompileSdk

                defaultConfig {
                    applicationId = androidApplicationId
                    minSdk = androidMinSdk
                    targetSdk = androidTargetSdk
                    versionCode = 1
                    versionName = "1.0"
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
        }
    }
}
