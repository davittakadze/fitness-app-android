plugins {
    id("betteryou.android.library")
    id("betteryou.android.hilt")
    id("betteryou.android.compose")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.betteryou.feature.settings.presentation"
}

dependencies {
    implementation(projects.core.presentation)
    implementation(projects.core.domain)
    implementation(projects.coreRes)
    implementation(projects.coreUi)
    implementation(projects.feature.settings.domain)
    implementation(projects.feature.settings.data)
    implementation(projects.util)
}