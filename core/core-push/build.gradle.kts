plugins {
    id("myapp.android.library")
}

android {
    namespace = "com.darkhorse.android.core.push"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}
