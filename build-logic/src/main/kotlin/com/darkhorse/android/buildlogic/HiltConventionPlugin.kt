package com.darkhorse.android.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class HiltConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            val hiltVersion = libs.findVersion("hilt").get().requiredVersion

            with(pluginManager) {
                apply("com.google.dagger.hilt.android")
                apply("com.google.devtools.ksp")
            }

            dependencies {
                add("implementation", "com.google.dagger:hilt-android:$hiltVersion")
                add("ksp", "com.google.dagger:hilt-compiler:$hiltVersion")
                add("testImplementation", "com.google.dagger:hilt-android-testing:$hiltVersion")
                add("kspTest", "com.google.dagger:hilt-android-compiler:$hiltVersion")
            }
        }
    }
}
