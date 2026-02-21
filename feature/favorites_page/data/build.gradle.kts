plugins {
    id("betteryou.android.library")
    id("betteryou.android.hilt")
    id("betteryou.android.serialization")
    id("betteryou.android.network")
}

android {
    namespace = "com.example.betteryou.feature.favorites_page.data"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.feature.favoritesPage.domain)
    implementation(projects.util)
}