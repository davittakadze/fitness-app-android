plugins {
    id("betteryou.android.library")
    id("betteryou.android.hilt")
    id("betteryou.android.serialization")
    id("betteryou.android.room")
    id("betteryou.android.network")
}

android {
    namespace = "com.betteryou.core.notifications"
}
dependencies {
    implementation(projects.core.domain)
    implementation(projects.coreUi)
    implementation("androidx.hilt:hilt-work:1.2.0")
    ksp("androidx.hilt:hilt-compiler:1.2.0")
}