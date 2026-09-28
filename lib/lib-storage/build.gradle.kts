plugins {
    id("myapp.android.library")
    id("myapp.android.hilt")
}

android {
    namespace = "com.darkhorse.android.lib.storage"
}

dependencies {
    implementation(project(":core:core-storage"))
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.kotlinx.coroutines.android)
}
