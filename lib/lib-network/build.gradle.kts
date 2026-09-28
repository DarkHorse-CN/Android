plugins {
    id("myapp.android.library")
    id("myapp.android.hilt")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.darkhorse.android.lib.network"
}

dependencies {
    implementation(project(":core:core-network"))

    // Network
    api(libs.retrofit)
    api(libs.retrofit.kotlinx.serialization)
    api(libs.okhttp)
    api(libs.okhttp.logging)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.android)
}
