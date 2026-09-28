plugins {
    id("myapp.android.library")
}

android {
    namespace = "com.darkhorse.android.core.notification"
}

dependencies {
    implementation(libs.androidx.core.ktx)
}
