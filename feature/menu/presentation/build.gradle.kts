plugins {
    id("betteryou.android.library")
    id("betteryou.android.hilt")
    id("betteryou.android.compose")
    id("betteryou.android.serialization")
}

android {
    namespace = "com.example.betteryou.feature.menu.presentation"
}

dependencies {
    // Google Auth & Play Services
    implementation(libs.play.services.auth.v2150)
    implementation(libs.kotlinx.coroutines.play.services)

    implementation(projects.core.domain)
    implementation(projects.core.presentation)
    implementation(projects.coreRes)
    implementation(projects.coreUi)
    implementation(projects.feature.menu.domain)
}