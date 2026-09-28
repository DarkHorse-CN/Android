plugins {
    id("myapp.android.library")
}

android {
    namespace = "com.darkhorse.android.libbase.dispatchers"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}
