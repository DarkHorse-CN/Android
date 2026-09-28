plugins {
    id("myapp.android.library")
}

android {
    namespace = "com.darkhorse.android.core.router"
}

dependencies {
    // Core:router 只包含接口定义，依赖最小化
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.serialization.json)
}
