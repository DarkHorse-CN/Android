plugins {
    id("myapp.android.library")
}

android {
    namespace = "com.darkhorse.android.lib.router"
}

dependencies {
    implementation(project(":core:core-router"))
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    // AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
}