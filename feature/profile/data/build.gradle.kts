plugins {
    id("betteryou.android.library")
    id("betteryou.android.hilt")
    id("betteryou.android.firebase")
}
android {
    namespace = "com.example.betteryou.feature.profile.data"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.feature.profile.domain)
}