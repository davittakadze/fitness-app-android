plugins {
    id("betteryou.android.library")
    id("betteryou.android.hilt")
    id("betteryou.android.firebase")
}

android {
    namespace = "com.example.data"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.feature.splash.domain)
}