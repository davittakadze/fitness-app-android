plugins {
    id("betteryou.android.library")
    id("betteryou.android.hilt")
    id("betteryou.android.compose")
    id("betteryou.android.serialization")
}

android {
    namespace = "com.example.betteryou.feature.profile.presentation"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(projects.core.presentation)
    implementation(projects.core.domain)

    implementation(projects.coreUi)
    implementation(projects.feature.profile.data)
    implementation(projects.feature.profile.domain)
    implementation(projects.util)
}