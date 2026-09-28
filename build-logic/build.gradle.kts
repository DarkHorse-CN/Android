plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

group = "com.darkhorse.android.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    jvmToolchain(17)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "myapp.android.application"
            implementationClass = "com.darkhorse.android.buildlogic.AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "myapp.android.library"
            implementationClass = "com.darkhorse.android.buildlogic.AndroidLibraryConventionPlugin"
        }
        register("compose") {
            id = "myapp.android.compose"
            implementationClass = "com.darkhorse.android.buildlogic.ComposeConventionPlugin"
        }
        register("hilt") {
            id = "myapp.android.hilt"
            implementationClass = "com.darkhorse.android.buildlogic.HiltConventionPlugin"
        }
        register("feature") {
            id = "myapp.android.feature"
            implementationClass = "com.darkhorse.android.buildlogic.FeatureConventionPlugin"
        }
        register("room") {
            id = "myapp.android.room"
            implementationClass = "com.darkhorse.android.buildlogic.RoomConventionPlugin"
        }
        register("testing") {
            id = "myapp.android.testing"
            implementationClass = "com.darkhorse.android.buildlogic.TestingConventionPlugin"
        }
    }
}

dependencies {
    compileOnly(libs.gradle)
    compileOnly(libs.kotlin.gradle.plugin)
    implementation(libs.hilt.gradle.plugin)
}
