plugins {
    id("myapp.android.library")
    id("myapp.android.compose")
}

android {
    namespace = "com.darkhorse.android.core.image"
}

dependencies {
    // Compose
    implementation(libs.androidx.compose.ui)

    // AndroidX
    implementation(libs.androidx.core.ktx)
}
