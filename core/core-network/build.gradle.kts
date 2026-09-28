plugins {
    id("myapp.android.library")
}

android {
    namespace = "com.darkhorse.android.core.network"
}

dependencies {
    // 只依赖接口定义所需的最小集合
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)
}
