pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("androidx.*")
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google {
            content {
                includeGroupByRegex("androidx.*")
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
            }
        }
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

includeBuild("build-logic")

rootProject.name = "App"

// ─── App Shell ───
include(":app")

// ─── Core: 纯抽象接口层（定义契约，不含实现） ───
include(":core:core-common")
include(":core:core-network")
include(":core:core-storage")
include(":core:core-ui")
include(":core:core-image")
include(":core:core-router")
include(":core:core-log")
include(":core:core-permission")
include(":core:core-push")
include(":core:core-analytics")
include(":core:core-network-compiler")
include(":core:core-notification")

// ─── Lib: 具体实现层（core 接口的实现） ───
include(":lib:lib-router")
include(":lib:lib-network")
include(":lib:lib-log")
include(":lib:lib-permission")
include(":lib:lib-storage")

// ─── Lib-Base: 纯基础工具库（位于 lib/ 下，无业务语义） ───
include(":lib:lib-base:base-util")

// ─── Feature: 业务特性模块 ───
include(":feature:feature-main")
include(":feature:feature-splash")
include(":feature:feature-home")
include(":feature:feature-profile")
include(":feature:feature-settings")
include(":feature:feature-login")
include(":feature:feature-onboarding")
