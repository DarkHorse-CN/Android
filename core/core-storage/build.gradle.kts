plugins {
    id("myapp.android.library")
}

android {
    namespace = "com.darkhorse.android.core.storage"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}
