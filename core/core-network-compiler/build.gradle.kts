plugins {
    id("myapp.android.library")
}

android {
    namespace = "com.darkhorse.android.core.network.compiler"
}

dependencies {
    // KSP 符号处理 API
    implementation(libs.ksp.api)

    // 依赖 core-network 以解析 @ApiRequest / DhRequest / DhResponse
    implementation(project(":core:core-network"))

    // KotlinPoet 用于类型安全的代码生成
    implementation(libs.kotlinpoet)
    implementation(libs.kotlinpoet.ksp)
}
