plugins {
    id("betteryou.android.library")
    id("betteryou.android.hilt")
    id("betteryou.android.compose")
    id("betteryou.android.serialization")
}
android {
    namespace = "com.betteryou.feature.register.presentation"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.presentation)

    implementation(projects.coreUi)
    implementation(projects.feature.register.domain)
}