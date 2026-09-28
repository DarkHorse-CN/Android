plugins {
    id("myapp.android.feature")
}

android {
    namespace = "com.darkhorse.android.feature.onboarding"
}

dependencies {
    // OnboardingActivity navigates to HomeActivity
    implementation(project(":feature:feature-home"))
}

