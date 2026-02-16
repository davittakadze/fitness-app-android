plugins {
    id("betteryou.android.library")
    id("betteryou.android.hilt")
    id("betteryou.android.firebase")
    id("betteryou.android.serialization")
    id("betteryou.android.network")
    id("betteryou.android.workmanager")
}
android {
    namespace = "com.example.betteryou.feature.daily.data"
}

dependencies {
    ksp(libs.androidx.room.compiler)
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.feature.daily.domain)
    implementation(projects.util)
}