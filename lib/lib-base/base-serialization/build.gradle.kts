plugins {
    id("myapp.android.library")
}

android {
    namespace = "com.darkhorse.android.libbase.serialization"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
}
