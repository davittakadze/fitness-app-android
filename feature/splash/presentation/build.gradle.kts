plugins {
    id("betteryou.android.library")
    id("betteryou.android.hilt")
    id("betteryou.android.compose")
    id("betteryou.android.serialization")
}

android {
    namespace = "com.example.presentation"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.presentation)
    implementation(projects.feature.splash.domain)
    implementation(projects.coreRes)
    implementation(projects.coreUi)
    implementation(projects.feature.splash.domain)
}