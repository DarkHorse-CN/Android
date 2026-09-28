plugins {
    id("myapp.android.feature")
}

android {
    namespace = "com.darkhorse.android.feature.main"
}

dependencies {
    api(project(":feature:feature-home"))
}
