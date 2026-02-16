plugins {
    id("betteryou.android.library")
    id("betteryou.android.hilt")
    id("betteryou.android.firebase")
    id("betteryou.android.serialization")
    id("betteryou.android.room")
    id("betteryou.android.datastore")
    id("betteryou.android.network")
}

android {
    namespace = "com.example.betteryou.data"
}

dependencies {
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.logging.interceptor)

    implementation(projects.core.domain)
    implementation(projects.core.model)
}