plugins {
    id("betteryou.android.library")
    id("betteryou.android.hilt")
    id("betteryou.android.compose")
    id("betteryou.android.serialization")
}

android {
    namespace = "com.example.betteryou.feature.favorites_page.presentation"
}

dependencies {
    implementation(projects.core.presentation)
    implementation(projects.core.domain)

    implementation(projects.coreUi)
    implementation(projects.util)
    implementation(projects.feature.favoritesPage.domain)
    implementation(projects.feature.favoritesPage.data)
}