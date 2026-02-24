plugins {
    id("betteryou.android.library")
    id("betteryou.android.hilt")
    id("betteryou.android.firebase")
    id("betteryou.android.serialization")
    id("betteryou.android.datastore")
}
android {
    namespace = "com.betteryou.feature.explore.data"
}

dependencies {
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.logging.interceptor)

    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.feature.explore.domain)
}
