plugins {
    id("myapp.android.library")
    id("myapp.android.hilt")
}

android {
    namespace = "com.darkhorse.android.lib.permission"
}

dependencies {
    implementation(project(":core:core-permission"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
}
