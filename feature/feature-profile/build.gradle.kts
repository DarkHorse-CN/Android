plugins {
    id("myapp.android.feature")
}

android {
    namespace = "com.darkhorse.android.feature.profile"
}

dependencies {
    // ProfileViewModel observes AuthRepository login state
    implementation(project(":feature:feature-login"))
}
