plugins {
    id("myapp.android.application")
    id("myapp.android.compose")
    id("myapp.android.hilt")
}

android {
    namespace = "com.darkhorse.android.app"
    defaultConfig {
        applicationId = "com.darkhorse.android"
    }
}

configurations.all {
    resolutionStrategy {
        force("org.jetbrains.kotlin:kotlin-stdlib:2.3.20")
        force("org.jetbrains.kotlin:kotlin-stdlib-common:2.3.20")
    }
}

dependencies {
    // Feature 模块
    implementation(project(":feature:feature-main"))
    implementation(project(":feature:feature-splash"))
    implementation(project(":feature:feature-login"))
    implementation(project(":feature:feature-onboarding"))

    // Lib 层（提供 Hilt 绑定：Retrofit, ILogger, IPermission, IPreferences 等）
    implementation(project(":lib:lib-network"))
    implementation(project(":lib:lib-log"))
    implementation(project(":lib:lib-permission"))
    implementation(project(":lib:lib-storage"))

    // Activity Compose (ComponentActivity.setContent, enableEdgeToEdge)
    implementation(libs.androidx.activity.compose)

    // App Startup (InitializationProvider, Initializer interface)
    implementation(libs.androidx.startup.runtime)

    // Testing
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(libs.junit)
}
