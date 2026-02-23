plugins {
    id("betteryou.android.library")
    id("betteryou.android.hilt")
    id("betteryou.android.firebase")
    id("betteryou.android.serialization")
    id("betteryou.android.network")
    id("betteryou.android.workmanager")
}

android {
    namespace = "com.betteryou.feature.notification.data"
}

dependencies {
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.logging.interceptor)

    implementation("androidx.hilt:hilt-work:1.2.0")
    ksp("androidx.hilt:hilt-compiler:1.2.0")

    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.core.notification)
    implementation(projects.coreUi)
    implementation(projects.feature.notification.domain)
}
