plugins {
    id("myapp.android.library")
    id("myapp.android.hilt")
}

android {
    namespace = "com.darkhorse.android.lib.log"
}

dependencies {
    implementation(project(":core:core-log"))

    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")
}
