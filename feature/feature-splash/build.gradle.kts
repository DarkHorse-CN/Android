plugins {
    id("myapp.android.feature")
}

android {
    namespace = "com.darkhorse.android.feature.splash"
}

dependencies {
    // SplashActivity navigates to OnboardingActivity and HomeActivity
    implementation(project(":feature:feature-onboarding"))
    implementation(project(":feature:feature-home"))
}
