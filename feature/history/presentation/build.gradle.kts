plugins {
    id("betteryou.android.library")
    id("betteryou.android.hilt")
    id("betteryou.android.compose")
    id("betteryou.android.serialization")
    id("betteryou.android.test")
}

android {
    namespace = "com.betteryou.feature.history.presentation"
}

dependencies {
    implementation(projects.core.presentation)
    implementation(projects.core.domain)

    implementation(projects.coreUi)
    implementation(projects.core.model)
    implementation(projects.feature.history.domain)
    implementation(projects.util)
    implementation(projects.testUtil.testing)
}


