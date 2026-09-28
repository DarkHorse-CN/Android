plugins {
    id("myapp.android.library")
    id("myapp.android.compose")
}

android {
    namespace = "com.darkhorse.android.core.ui"
}

dependencies {
    implementation(project(":core:core-common"))
    implementation(project(":core:core-image"))

    // AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Material Icons (用于 AppTopAppBar 等通用组件)
    implementation(libs.compose.icons.core)

    // Coil (image loading for UI components)
    implementation(libs.coil.compose)
}
