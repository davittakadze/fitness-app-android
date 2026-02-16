plugins {
    id("betteryou.android.library")
    id("betteryou.android.hilt")
    id("betteryou.android.firebase")
    id("betteryou.android.serialization")
}
android {
    namespace = "com.betteryou.workout.data"
}

dependencies {
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.logging.interceptor)

    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.core.model)
    implementation(projects.feature.workout.domain)
}