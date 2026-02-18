plugins {
    id("betteryou.android.library")
    id("betteryou.android.hilt")
    id("betteryou.android.compose")
    id("betteryou.android.serialization")
}

android {
    namespace = "com.betteryou.workout.presentation"
}

dependencies {
    implementation(projects.core.presentation)
    implementation(projects.core.domain)

    implementation(projects.coreUi)
    implementation(projects.core.model)
    implementation(projects.feature.workout.domain)
}