plugins {
    id("betteryou.android.library")
    id("betteryou.android.hilt")
    id("betteryou.android.compose")
    id("betteryou.android.serialization")
}

android {
    namespace = "com.example.betteryou.feature.daily.presentation"
}

dependencies {
    implementation(projects.core.presentation)
    implementation(projects.core.domain)
    
    implementation(projects.coreUi)
    implementation(projects.util)
    implementation(projects.feature.daily.domain)
    implementation(projects.feature.daily.data)
}