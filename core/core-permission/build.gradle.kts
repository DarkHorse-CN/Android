plugins {
    id("myapp.android.library")
}

android {
    namespace = "com.darkhorse.android.core.permission"
}

dependencies {
    implementation(libs.androidx.core.ktx)
}
