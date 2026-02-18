plugins {
    id("betteryou.android.library")
    id("betteryou.android.hilt")
    id("betteryou.android.compose")
    id("betteryou.android.serialization")
}

android {
    namespace = "com.example.betteryou.feature.recipes.presentation"
}

dependencies {
    implementation(projects.core.presentation)
    implementation(projects.core.domain)

    implementation(projects.coreUi)
    implementation(projects.util)
    implementation(projects.feature.recipes.domain)
    implementation(projects.feature.recipes.data)
}