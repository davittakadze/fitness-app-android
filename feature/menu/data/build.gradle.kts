plugins {
    id("betteryou.android.library")
    id("betteryou.android.hilt")
}

android {
    namespace = "com.example.betteryou.feature.menu.data"
}

dependencies {
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)

    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.feature.menu.domain)
}