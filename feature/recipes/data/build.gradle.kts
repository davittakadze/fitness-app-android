plugins {
    id("betteryou.android.library")
    id("betteryou.android.hilt")
    id("betteryou.android.serialization")
    id("betteryou.android.network")
}

android {
    namespace = "com.example.betteryou.feature.recipes.data"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.feature.recipes.domain)
    implementation(projects.util)
}