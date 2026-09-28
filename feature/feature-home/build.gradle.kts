plugins {
    id("myapp.android.feature")
}

android {
    namespace = "com.darkhorse.android.feature.home"
}

dependencies {
    // ProfileScreen in bottom nav tab
    implementation(project(":feature:feature-profile"))
    // LoginScreen & RegisterScreen in NavHost
    implementation(project(":feature:feature-login"))
}
