plugins {
    id("myapp.android.library")
}

android {
    namespace = "com.darkhorse.android.libbase.util"
}

dependencies {
    implementation(libs.androidx.core.ktx)
}
